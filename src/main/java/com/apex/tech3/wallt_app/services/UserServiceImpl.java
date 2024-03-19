package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.*;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TokenService;
import com.apex.tech3.wallt_app.helpers.TransactionMapper;
import com.apex.tech3.wallt_app.helpers.TransferMapper;
import com.apex.tech3.wallt_app.models.AdminFinancialActivity;
import com.apex.tech3.wallt_app.models.FinancialActivity;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import com.apex.tech3.wallt_app.models.filters.UserSpecification;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import com.apex.tech3.wallt_app.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNAUTHORIZED_USER_ERROR = "You are not authorized to perform this operation";
    private final UserRepository repository;
    private final TokenService tokenService;
    private final EmailServiceImpl emailService;

    private final TransactionService transactionService;

    private final TransferService transferService;
    private final WalletService walletService;
    private final CurrencyService currencyService;

    private final TransactionMapper transactionMapper;

    private final TransferMapper transferMapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, TokenService tokenService, EmailServiceImpl emailService,
                           TransactionService transactionService, TransferService transferService, WalletService walletService, CurrencyService currencyService, TransactionMapper transactionMapper, TransferMapper transferMapper) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.transactionService = transactionService;
        this.transferService = transferService;
        this.walletService = walletService;
        this.currencyService = currencyService;
        this.transactionMapper = transactionMapper;
        this.transferMapper = transferMapper;
    }


    @Override
    public User getById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getByToken(String token) {
        return repository.findByConfirmationToken(token);
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                             String middleName, String lastName, String email,
                             String phone) {
        if (pageable == null) {
            List<User> users = repository.findAll(UserSpecification.filterByAllColumns(id, username, firstName, middleName, lastName, email, phone));
            return new PageImpl<>(users);
        } else {
            return repository.findAll(UserSpecification.filterByAllColumns(id, username, firstName, middleName, lastName, email, phone), pageable);
        }
    }

    @Override
    public List<User> getAllActiveAndVerified() {
        return repository.findAll(UserSpecification.getAllActiveAndVerified());
    }

    @Transactional
    @Override
    public User register(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
        if (repository.existsByPhone(user.getPhone())) {
            throw new EntityDuplicateException("User", "phone", user.getPhone());
        }
        sendConfirmationEmail(user);
        User curr = repository.save(user);
        Wallet wallet = new Wallet();
        wallet.setAmount(BigDecimal.ZERO);
        wallet.setCurrency(currencyService.getById(2));
        wallet.setDefault(true);
        wallet.setActive(true);
        walletService.create(wallet, curr);
        return curr;
    }

    @Override
    public void sendConfirmationEmail(User user) {
        String confirmationToken = tokenService.generateToken();
        user.setConfirmationToken(confirmationToken);
        emailService.sendConfirmationEmail(user.getEmail(), confirmationToken);
    }

    @Override
    public void confirmUser(String token) {
        User user = repository.findByConfirmationToken(token);
        if (user == null || user.isVerified()) {
            throw new EntityNotFoundException("User", "confirmation token", token);
        }
        if (tokenService.isValidToken(token)) {
            user.setVerified(true);
            user.setConfirmationToken(null);
            repository.save(user);
        } else {
            throw new InvalidTokenException("Invalid confirmation token");
        }
    }

    @Override
    public void confirmResetPasswordToken(String token) {
        User user = repository.findByConfirmationToken(token);
        if (user == null) {
            throw new EntityNotFoundException("User", "confirmation token", token);
        }
        if (tokenService.isValidToken(token)) {
            repository.save(user);
        } else {
            throw new InvalidTokenException("Invalid confirmation token");
        }
    }

    @Override
    public void handleForgottenPassword(String username, String email) {
        User user = repository.getByEmail(email);//todo find by email and username
        if (user == null) {
            throw new EntityNotFoundException("User with this username and email not found");
        }
        String confirmationToken = tokenService.generateToken();
        user.setConfirmationToken(confirmationToken);
        emailService.sendResetPasswordEmail(email, confirmationToken);
        repository.save(user);
    }

    @Override
    public void changePassword(PasswordRecoveryDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new InvalidPasswordException("Passwords don't match");
        }
        User user = repository.findByConfirmationToken(dto.getToken());
        user.setPassword(dto.getPassword());
        user.setConfirmationToken(null);
        repository.save(user);
    }

    @Override
    public User update(User user, User requester) {
        tryAuthorizeUser(user, requester);
        checkIfUniqueEmail(user);
        return repository.save(user);
    }

    private void checkIfUniqueEmail(User user) {
        boolean duplicateExists = true;
        try {
            User userByEmail = repository.getByEmail(user.getEmail());
            if (userByEmail == null || userByEmail.getId() == user.getId())
                duplicateExists = false;
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
    }


    private void tryAuthorizeUser(User user, User requester) {
        if (!user.getUsername().equals(requester.getUsername()) || AuthenticationHelper.isBlocked(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
    }

    User addUneditableAttributes(User old, User updated) {
        updated.setBlocked(old.isBlocked());
        updated.setVerified(old.isVerified());
        updated.setStampCreated(old.getStampCreated());
        updated.setDeleted(false);
        return updated;
    }

    User validateNewPassword(UserUpdateDto toUpdate, User updated) {
        String newPassword = toUpdate.getNewPassword();
        String passwordConfirmation = toUpdate.getPasswordConfirmation();

        if (newPassword.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[+\\-*&^]).{8,}$")
                && newPassword.equals(passwordConfirmation)) {
            updated.setPassword(newPassword);
            return updated;
        } else {
            throw new InvalidPasswordException("Invalid new password. Please ensure it meets the following criteria: "
                    + "at least 8 characters long, contains at least one uppercase letter, one digit, and one special character.");
        }
    }

    @Override
    public boolean isAdmin(User admin) {
        return admin.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"));
    }

    @Override
    public User blockUser(int userId, User requester) {
        if (!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeBlocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));

        userToBeBlocked.setBlocked(true);
        return repository.save(userToBeBlocked);
    }

    @Override
    public User unblockUser(int userId, User requester) {
        if (!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeUnblocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeUnblocked.setBlocked(false);
        return repository.save(userToBeUnblocked);
    }

    @Override
    public User switchBlockedStatus(int userId, User requester) {
        User user = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        if (isAdmin(requester)) {
            user.setBlocked(!user.isBlocked());
            return repository.save(user);
        } else {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
    }

    @Override
    public void deleteUser(int userId, User requester) {
        if (!isAdmin(requester) && userId != requester.getId()) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeDeleted = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeDeleted.setDeleted(true);
        repository.save(userToBeDeleted);
    }

    @Override
    public User restoreUser(int userId, User requester) {
        if (!isAdmin(requester) && userId != requester.getId()) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeRestored = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeRestored.setDeleted(true);
        return repository.save(userToBeRestored);
    }

    @Override
    public List<FinancialActivity> collectActivity(int userId) {

        List<FinancialActivity> transactions = transactionService.getByUserId(userId).stream().map(t -> t.getSenderWallet().getHolder().getId() == userId ?
                transactionMapper.moneySentToActivity(t) : transactionMapper.moneyReceivedToActivity(t)).toList();

        List<FinancialActivity> transfers = transferService.getUserTransfers(userId).stream()
                .map(t -> t.getDirection() == DirectionEnum.DEPOSIT ?
                        transferMapper.depositToActivity(t) : transferMapper.withdrawalToActivity(t)).toList();

        List<FinancialActivity> activities = new ArrayList<>(transactions.size() + transfers.size());
        activities.addAll(transactions);
        activities.addAll(transfers);
        activities.sort(Comparator.comparing(FinancialActivity::getTimestamp).reversed());
        return activities;
    }

    @Override
    public List<AdminFinancialActivity> collectAllActivity() {

        List<AdminFinancialActivity> transactions = transactionService.getAll()
                .stream().map(transactionMapper::toAdminActivity).toList();

        List<AdminFinancialActivity> transfers = transferService.getAllTransfers().stream()
                .map(t -> t.getDirection() == DirectionEnum.DEPOSIT ?
                        transferMapper.toAdminDeposit(t) : transferMapper.toAdminWithdrawal(t)).toList();

        List<AdminFinancialActivity> activities = new ArrayList<>(transactions.size() + transfers.size());
        activities.addAll(transactions);
        activities.addAll(transfers);
//        activities.sort(Comparator.comparing(AdminFinancialActivity::getTimestamp).reversed());
        activities.sort((a1, a2) -> Long.compare(a2.getTimestamp().getTime(), a1.getTimestamp().getTime()));
        return activities;
    }

    public Map<String, BigDecimal> collectStats(int userId) {
        BigDecimal totalSent = transactionService.getSentAmountByUserId(userId);
        BigDecimal totalReceived = transactionService.getReceivedAmountByUserId(userId);
        Map<String, BigDecimal> stats = new HashMap<>();
        if (totalSent == null) totalSent = BigDecimal.ZERO;
        if (totalReceived == null) totalReceived = BigDecimal.ZERO;
        stats.put("totalSent", totalSent);
        stats.put("totalReceived", totalReceived);
        return stats;
    }

    @Override
    public Map<String, Object> collectActivityAndStats(int userId) {
        Map<String, Object> activityAndStats = new HashMap<>();
        activityAndStats.put("activities", collectActivity(userId));
        activityAndStats.put("stats", collectStats(userId));
        return activityAndStats;
    }


}

package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.*;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TokenService;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.filters.UserSpecification;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNAUTHORIZED_USER_ERROR = "You are not authorized to perform this operation";
    private final UserRepository repository;
    private final TokenService tokenService;
    private final EmailServiceImpl emailService;

    @Autowired
    public UserServiceImpl(UserRepository repository, TokenService tokenService, EmailServiceImpl emailService) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public User getById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                                    String middleName, String lastName, String email,
                                    String phone) {
        if(pageable == null) {
            List<User> users = repository.findAll(UserSpecification.filterByAllColumns(id, username, firstName, middleName, lastName, email, phone));
            return new PageImpl<>(users);
        } else {
            return repository.findAll(UserSpecification.filterByAllColumns(id, username, firstName, middleName, lastName, email, phone), pageable);
        }
    }

    @Override
    public User register(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }
        sendConfirmationEmail(user);
        return repository.save(user);
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
    public User update(User user, User requester, UserUpdateDto dto, int id) {
        User userToUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        tryAuthorizeUser(requester, userToUpdate);
        checkIfUniqueEmail(user, requester);
        User updatedUser = addUneditableAttributes(userToUpdate, user);
        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            updatedUser = validateNewPassword(dto, updatedUser);
        } else {
            updatedUser.setPassword(requester.getPassword());
        }
        return repository.save(updatedUser);
    }

    private void checkIfUniqueEmail(User user, User requester) {
        if (Objects.equals(user.getEmail(), requester.getEmail())) {
            return;
        }
        boolean duplicateExists = true;
        User userByEmail = repository.getByEmail(user.getEmail());
        if (userByEmail == null) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
    }

    private void tryAuthorizeUser(User user, User requester) {
        if (!user.getUsername().equals(requester.getUsername()) && AuthenticationHelper.isBlocked(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
    }

    private User addUneditableAttributes(User old, User updated) {
        updated.setBlocked(old.isBlocked());
        updated.setVerified(old.isVerified());
        updated.setStampCreated(old.getStampCreated());
        updated.setDeleted(false);
        return updated;
    }

    private User validateNewPassword(UserUpdateDto toUpdate, User updated) {
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
        if(!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeBlocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));

        userToBeBlocked.setBlocked(true);
        return repository.save(userToBeBlocked);
    }

    @Override
    public User unblockUser(int userId, User requester) {
        if(!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeUnblocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeUnblocked.setBlocked(false);
        return repository.save(userToBeUnblocked);
    }

    @Override
    public void deleteUser(int userId, User requester) {
        if (!isAdmin(requester) || userId != requester.getId()) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeDeleted = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeDeleted.setDeleted(true);
        repository.save(userToBeDeleted);
    }

    @Override
    public User restoreUser(int userId, User requester) {
        if (!isAdmin(requester) || userId != requester.getId()) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeRestored = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeRestored.setDeleted(true);
        return repository.save(userToBeRestored);
    }


}

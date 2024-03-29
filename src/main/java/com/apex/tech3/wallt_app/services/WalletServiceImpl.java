package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.models.filters.WalletSpecification;
import com.apex.tech3.wallt_app.repositories.WalletRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    private static final int PAGE_SIZE = 5;
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet getById(int id) {
        Wallet wallet = repository.findByIdAndIsActiveTrue(id);
        if (wallet == null) {
            throw new EntityNotFoundException("Wallet", id);
        }
        return wallet;
    }

    @Override
    public List<Wallet> getAll(Integer holderId, BigDecimal amountGreaterThan, BigDecimal amountLessThan, Integer currencyId) {
        WalletFilterOptions filterOptions = new WalletFilterOptions();
        filterOptions.setHolderId(holderId);
        filterOptions.setAmountGreaterThan(amountGreaterThan);
        filterOptions.setAmountLessThan(amountLessThan);
        filterOptions.setCurrencyId(currencyId);
        return repository.findAll(WalletSpecification.filterByAllColumns(holderId, amountGreaterThan, amountLessThan, currencyId),
                createPageable(filterOptions)).stream().toList();
    }

    @Override
    public Page<Wallet> getAll(WalletFilterOptions filterOptions) {
        return repository.findAll(WalletSpecification.filterByAllColumns(filterOptions.getHolderId(),
                        filterOptions.getAmountGreaterThan(), filterOptions.getAmountLessThan(),
                        filterOptions.getCurrencyId()),
                createPageable(filterOptions));
    }

    private Pageable createPageable(WalletFilterOptions filterOptions) {
        if (filterOptions.getSortBy() == null || filterOptions.getSortBy().isEmpty())
            return PageRequest.of(filterOptions.getPage(), PAGE_SIZE);
        Sort sort = Sort.by(filterOptions.getSortBy());
        sort = filterOptions.getSortOrder().equals("desc") ? sort.descending() : sort.ascending();
        return PageRequest.of(filterOptions.getPage(), PAGE_SIZE, sort);
    }

    @Override
    public List<Wallet> getByUserId(int userId) {
        return repository.findByHolderId(userId).stream().toList();
    }

    @Override
    public List<Wallet> getActiveByUserId(int userId) {
        return repository.findByHolderIdAndIsActiveTrue(userId).stream().toList();
    }

    @Override
    public BigDecimal getTotalBalance(int userId) {
        BigDecimal total = repository.getTotalBalance(userId);
        if(total.compareTo(BigDecimal.ONE) > 0) return total.setScale(2, RoundingMode.HALF_UP);
        else if(total.equals(BigDecimal.ZERO)) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        else return total.setScale(6, RoundingMode.HALF_UP);
    }

    @Override
    public Wallet create(Wallet wallet, User user) {
        wallet.setHolder(user);
        wallet.setActive(true);
        return repository.save(wallet);
    }

    @Override
    public Wallet update(Wallet wallet) {
        return repository.save(wallet);
    }


    @Override
    public void changeStatus(int id, User user) {
        Wallet wallet = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Wallet", id));
        if (wallet.getHolder().getId() != user.getId()) {
            throw new AuthorizationException("You are not the owner of this wallet.");
        }
        wallet.setActive(!wallet.isActive());
        repository.save(wallet);
    }

    @Override
    public void changeDefaultWallet(int id, User user) {
        Wallet wallet = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Wallet", id));
        if (wallet.getHolder().getId() != user.getId()) {
            throw new AuthorizationException("You are not the owner of this wallet.");
        }
        if (wallet.isDefault()) return;
        Wallet oldDefault = repository.findByHolderIdAndIsDefaultTrue(user.getId());
        oldDefault.setDefault(false);
        if (!wallet.isActive()) wallet.setActive(true);
        wallet.setDefault(true);
        repository.save(wallet);
    }

    @Override
    public void delete(int id) {
        Wallet wallet = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Wallet", id));
        wallet.setActive(false);
        repository.save(wallet);
    }

    public static void checkIfFundsAreAvailable(Wallet wallet, BigDecimal amount) {
        if (wallet.getAmount().compareTo(amount) < 0) throw new InsufficientFundsException("Insufficient funds");
    }

    @Override
    public void creditAmount(Wallet wallet, BigDecimal amount) {
        checkIfFundsAreAvailable(wallet, amount);
        wallet.setAmount(wallet.getAmount().subtract(amount));
    }

    @Override
    public void debitAmount(Wallet wallet, BigDecimal amount) {
        wallet.setAmount(wallet.getAmount().add(amount));
    }

    @Override
    public void checkOwnership(Wallet wallet, User user) {
        if (wallet.getHolder() != user) throw new AuthorizationException("You are not the owner of this wallet");
    }

    @Override
    public Wallet getDefaultWalletByHolderId(int holderId) {
        return repository.findByHolderIdAndIsDefaultTrue(holderId);
    }

    @Override
    public Wallet getSameOrDefaultWalletByHolderId(int holderId, int walletId) {
        Currency currency = getById(walletId).getCurrency();
        if (repository.findByHolderIdAndCurrencyId(holderId, currency.getId()) != null) {
            return repository.findByHolderIdAndCurrencyId(holderId, currency.getId());
        }

        return repository.findByHolderIdAndIsDefaultTrue(holderId);
    }


}

package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    Wallet getById(int id);

    List<Wallet> getAll(Integer holderId, BigDecimal amountGreaterThan, BigDecimal amountLessThan, Integer currencyId);

    Page<Wallet> getAll(WalletFilterOptions filterOptions);

    List<Wallet> getByUserId(int userId);

    List<Wallet> getActiveByUserId(int userId);

    BigDecimal getTotalBalance(int userId);

    Wallet create(Wallet wallet, User user);

    Wallet update(Wallet wallet);

    void delete(int id);

    void creditAmount(Wallet wallet, BigDecimal amount);

    void debitAmount(Wallet wallet, BigDecimal amount);

    void checkOwnership(Wallet wallet, User user);
}

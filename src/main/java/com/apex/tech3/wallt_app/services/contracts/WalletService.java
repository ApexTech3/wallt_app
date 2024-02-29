package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WalletService {
    Wallet get(int id);

    List<Wallet> getAll();

    Page<Wallet> getAllFilteredSortedAndPaginated(WalletFilterOptions filterOptions);

    List<Wallet> getByUserId(int userId);

    Wallet create(Wallet wallet);

    Wallet update(Wallet wallet);

    void delete(int id);
}

package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import org.springframework.data.domain.Page;

public interface WalletService {
    Wallet get(int id);

    Page<Wallet> getAll(WalletFilterOptions filterOptions);
}

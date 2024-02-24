package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Wallet;

import java.util.List;

public interface WalletService {
    Wallet get(int id);

    List<Wallet> getAll();

    List<Wallet> getByHolderAndCurrency(Integer holderId, Integer currencyId);
}

package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;

import java.math.BigDecimal;

public class FundsHelper {
    private static final String INSUFFICIENT_FUNDS_ERROR = "Insufficient funds.";

    public static void validateWalletHasEnoughFunds(BigDecimal amount, BigDecimal walletAmount) {
        if (amount.compareTo(walletAmount) > 0)
            throw new InsufficientFundsException(INSUFFICIENT_FUNDS_ERROR);
    }
}

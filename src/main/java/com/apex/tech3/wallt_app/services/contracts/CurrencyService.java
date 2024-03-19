package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Currency;

import java.util.List;

public interface CurrencyService {
    Currency getById(int id);

    Currency getByTicker(String ticker);

    double getRateToUsd(String ticker);

    double getRate(String baseCurrencyTicker, String quotedCurrencyTicker);

    double getRate(Currency baseCurrency, Currency quotedCurrency);

    List<Currency> getAllAvailableCurrenciesForUserWallets(int userId);
}

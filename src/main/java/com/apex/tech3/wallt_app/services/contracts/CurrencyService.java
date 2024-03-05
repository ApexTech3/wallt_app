package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Currency;

public interface CurrencyService {
    Currency get(int id);

    Currency get(String ticker);

    double getRateToUsd(String ticker);

    double getRate(String baseCurrencyTicker, String quotedCurrencyTicker);

    double getRate(Currency baseCurrency, Currency quotedCurrency);
}

package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.repositories.CurrencyRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Currency getById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency", id));
    }

    @Override
    public Currency getByTicker(String ticker) {
        return repository.findByTicker(ticker);
    }

    @Override
    public double getRateToUsd(String ticker) {
        return getByTicker(ticker).getRateToUsd();
    }

    @Override
    public double getRate(String baseCurrencyTicker, String quotedCurrencyTicker) {
        return getRateToUsd(baseCurrencyTicker) / getRateToUsd(quotedCurrencyTicker);
    }

    @Override
    public double getRate(Currency baseCurrency, Currency quotedCurrency) {
        return getRateToUsd(baseCurrency.getTicker()) / getRateToUsd(quotedCurrency.getTicker());
    }
}

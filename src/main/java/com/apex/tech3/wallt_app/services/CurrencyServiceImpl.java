package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.repositories.CurrencyRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repository;
    private final WalletService walletService;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository repository, WalletService walletService) {
        this.repository = repository;
        this.walletService = walletService;
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

    @Override
    public List<Currency> getAllAvailableCurrenciesForUserWallets(int userId) {
        List<Wallet> byUserId = walletService.getByUserId(userId);
        List<Currency> all = repository.findAll();
        all.removeAll(byUserId.stream().map(Wallet::getCurrency).toList());
        return all;
    }
}

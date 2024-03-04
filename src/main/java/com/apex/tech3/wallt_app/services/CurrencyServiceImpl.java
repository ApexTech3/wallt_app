package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.repositories.CurrencyRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private CurrencyRepository repository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Currency get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency", id));
    }

    @Override
    public Currency get(String ticker) {
        return repository.findByTicker(ticker);
    }
}

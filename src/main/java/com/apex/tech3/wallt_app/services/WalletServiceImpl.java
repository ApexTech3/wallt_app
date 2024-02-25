package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.repositories.contracts.WalletRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet get(int id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Wallet> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Wallet> getByHolderAndCurrency(WalletFilterOptions filterOptions) {
        return repository.findByHolderAndCurrency(filterOptions.getHolderId(), filterOptions.getAmountGreaterThan(),
                filterOptions.getAmountLessThan(), filterOptions.getCurrencyId());
    }
}

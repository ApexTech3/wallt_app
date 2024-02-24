package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.repositories.contracts.WalletRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
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
    public List<Wallet> getAll() {
        return repository.findAll();
    }
}

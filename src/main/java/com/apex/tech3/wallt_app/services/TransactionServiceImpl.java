package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.filters.TransactionSpecification;
import com.apex.tech3.wallt_app.repositories.TransactionRepository;
import com.apex.tech3.wallt_app.services.contracts.TransactionService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final WalletService walletService;

    private final UserService userService;


    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, WalletService walletService, UserService userService) {
        this.repository = repository;
        this.walletService = walletService;
        this.userService = userService;
    }


    @Override
    public Transaction get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction", id));
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, String currencySymbol, String status, Timestamp date) {
        if(pageable == null) {
            List<Transaction> transactions = repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount, currencySymbol, status, date));
            return new PageImpl<>(transactions);
        } else {
            return repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount, currencySymbol, status, date), pageable);
        }
    }

    public Page<Transaction> getAll2(Pageable pageable) {
        if(pageable == null) {
            List<Transaction> transactions = repository.findAll();
            return new PageImpl<>(transactions);
        } else {
            return repository.findAll(pageable);
        }
    }

    @Override
    public Transaction create(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction update(Transaction transaction) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}

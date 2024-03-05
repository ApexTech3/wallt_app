package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import com.apex.tech3.wallt_app.models.filters.TransactionSpecification;
import com.apex.tech3.wallt_app.repositories.TransactionRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.TransactionService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final WalletService walletService;

    private final CurrencyService currencyService;

    private final UserService userService;


    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, WalletService walletService, UserService userService, CurrencyService currencyService) {
        this.repository = repository;
        this.currencyService = currencyService;
        this.walletService = walletService;
        this.userService = userService;
    }


    @Override
    public Transaction getById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction", id));
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, String status, LocalDate date) {
        if(pageable == null) {
            repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount, status, date));
            return new PageImpl<>(repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount, status, date)));
        } else {
            return repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount, status, date), pageable);
        }
    }

    @Override
    public Transaction create(Transaction transaction, User user) {

        Wallet senderWallet = transaction.getSenderWallet();
        Wallet receiverWallet = transaction.getReceiverWallet();
        BigDecimal amountInSenderCurrency = transaction.getAmount();
        double exchangeRate = currencyService.getRate(senderWallet.getCurrency(), receiverWallet.getCurrency());
        BigDecimal amountInReceiverCurrency = amountInSenderCurrency.multiply(BigDecimal.valueOf(exchangeRate));

        transaction.setExchangeRate(exchangeRate);
        transaction.setAmount(amountInReceiverCurrency);
        try {
            walletService.checkOwnership(senderWallet, user);
            walletService.creditAmount(senderWallet, amountInSenderCurrency);
            walletService.debitAmount(receiverWallet, amountInReceiverCurrency);
            transaction.setStatus(StatusEnum.SUCCESSFUL);
            return repository.save(transaction);
        } catch(AuthorizationException e) {
            transaction.setStatus(StatusEnum.FAILED);
            repository.save(transaction);
            throw new AuthorizationException(e.getMessage());
        } catch(InsufficientFundsException e) {
            transaction.setStatus(StatusEnum.FAILED);
            repository.save(transaction);
            throw new InsufficientFundsException(e.getMessage());
        }
    }

}

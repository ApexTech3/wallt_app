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
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final WalletService walletService;

    private final CurrencyService currencyService;


    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, WalletService walletService, CurrencyService currencyService) {
        this.repository = repository;
        this.currencyService = currencyService;
        this.walletService = walletService;
    }


    @Override
    public List<Transaction> getByUserId(int userId) {
        return repository.findAll(TransactionSpecification.filterByUserId(userId));
    }

    @Override
    public List<Transaction> getBySenderId(int senderId) {
        return repository.findAll(TransactionSpecification.filterBySenderId(senderId));
    }

    @Override
    public List<Transaction> getByReceiverId(int receiverId) {
        return repository.findAll(TransactionSpecification.filterByReceiverId(receiverId));
    }

    @Override
    public Transaction getById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction", id));
    }

    @Override
    public Page<Transaction> getBySender(Pageable pageable, int senderId, String status) {
        return repository.findAll(TransactionSpecification.filterBySenderAndStatus(senderId, status), pageable);
    }

    @Override
    public Page<Transaction> getByReceiver(Pageable pageable, int receiverId, String status) {
        return repository.findAll(TransactionSpecification.filterByReceiverAndStatus(receiverId, status), pageable);
    }

    @Override
    public BigDecimal getSentAmountByUserId(int userId) {
        return repository.getSentAmountByUserId(userId);
    }

    @Override
    public BigDecimal getReceivedAmountByUserId(int userId) {
        return repository.getReceivedAmountByUserId(userId);
    }


    @Override
    public Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId,
                                    Double amount, Double amountGreaterThan, Double amountLesserThan, String status,
                                    LocalDate date, LocalDate laterThan, LocalDate earlierThan) {

        if (pageable == null || pageable.isUnpaged()) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "stampCreated"));
        }

        return repository.findAll(TransactionSpecification.filterByAllColumns(id, receiverWalletId, senderWalletId, amount,
                amountGreaterThan, amountLesserThan, status,
                date, laterThan, earlierThan), pageable);
    }

    @Override
    public List<Transaction> getAll() {
        return repository.findAll();
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
        } catch (AuthorizationException e) {
            transaction.setStatus(StatusEnum.FAILED);
            repository.save(transaction);
            throw new AuthorizationException(e.getMessage());
        } catch (InsufficientFundsException e) {
            transaction.setStatus(StatusEnum.FAILED);
            repository.save(transaction);
            throw new InsufficientFundsException(e.getMessage());
        }
    }

}

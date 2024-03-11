package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.FinancialActivity;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.dtos.TransactionDto;
import com.apex.tech3.wallt_app.models.dtos.TransactionResponse;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TransactionMapper {

    private final CurrencyService currencyService;
    private final WalletService walletService;

    @Autowired
    public TransactionMapper(CurrencyService currencyService, WalletService walletService) {
        this.currencyService = currencyService;
        this.walletService = walletService;
    }

    public Transaction fromDto(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setSenderWallet(walletService.getById(transactionDto.getSenderWalletId()));
        transaction.setReceiverWallet(walletService.getById(transactionDto.getReceiverWalletId()));
        return transaction;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmountSent(transaction.getAmount().divide(BigDecimal.valueOf(transaction.getExchangeRate())));
        transactionResponse.setExchangeRate(transaction.getExchangeRate());
        transactionResponse.setAmountReceived(transaction.getAmount());
        transactionResponse.setSenderWallet(transaction.getSenderWallet());
        transactionResponse.setReceiverWallet(transaction.getReceiverWallet());
        transactionResponse.setStatus(transaction.getStatus().toString());
        transactionResponse.setTimestamp(transaction.getStampCreated().toString());
        return transactionResponse;
    }

    public FinancialActivity moneySentToActivity(Transaction transaction) {
        FinancialActivity activity = new FinancialActivity();
        activity.setDescription("Money sent to " + transaction.getReceiverWallet().getHolder().getUsername());
        activity.setAmount(transaction.getAmount().negate().setScale(2, RoundingMode.HALF_UP));
        activity.setCurrencySymbol(transaction.getSenderWallet().getCurrency().getSymbol());
        activity.setTimestamp(transaction.getStampCreated());
        activity.setWalletId(transaction.getSenderWallet().getId());
        activity.setStatus(transaction.getStatus());
        activity.setType("SENT");
        return activity;
    }

    public FinancialActivity moneyReceivedToActivity(Transaction transaction) {
        FinancialActivity activity = new FinancialActivity();
        activity.setDescription("Money received from " + transaction.getSenderWallet().getHolder().getUsername());
        activity.setAmount(transaction.getAmount().setScale(2, RoundingMode.HALF_UP));
        activity.setCurrencySymbol(transaction.getReceiverWallet().getCurrency().getSymbol());
        activity.setTimestamp(transaction.getStampCreated());
        activity.setWalletId(transaction.getReceiverWallet().getId());
        activity.setStatus(transaction.getStatus());
        activity.setType("RECEIVED");
        return activity;
    }
}

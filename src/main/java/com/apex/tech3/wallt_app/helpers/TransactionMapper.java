package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.dtos.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    Transaction fromDto(TransactionDto transactionDto) {
        return null;
    }

    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCurrency(transaction.getCurrency());
        transactionDto.setReceiverWallet(transaction.getReceiverWallet());
        transactionDto.setSenderWallet(transaction.getSenderWallet());
        transactionDto.setStatus(transaction.getStatus());
        transactionDto.setTimestamp(transaction.getStampCreated());
        return transactionDto;
    }
}

package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface TransactionService {

    Transaction get(int id);

    Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, String currencySymbol, String status, Timestamp date);

    Transaction create(Transaction transaction, User user);
}

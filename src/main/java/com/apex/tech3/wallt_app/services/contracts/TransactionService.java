package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TransactionService {

    Transaction getById(int id);

    Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, String status, LocalDate date);

    Transaction create(Transaction transaction, User user);
}

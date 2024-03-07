package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<Transaction> getByUserId(int userId);

    List<Transaction> getBySenderId(int senderId);

    List<Transaction> getByReceiverId(int receiverId);

    Transaction getById(int id);

    Page<Transaction> getBySender(Pageable pageable, int senderId, String status);

    Page<Transaction> getByReceiver(Pageable pageable, int receiverId, String status);

    Page<Transaction> getAll(Pageable pageable, Integer id, Integer receiverWalletId, Integer senderWalletId,
                             Double amount, Double amountGreaterThan, Double amountLesserThan,
                             String status, LocalDate date, LocalDate laterThan, LocalDate earlierThan);

    Transaction create(Transaction transaction, User user);
}

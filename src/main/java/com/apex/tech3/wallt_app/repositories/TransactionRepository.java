package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {

    @Query("SELECT ROUND(SUM(t.amount* c.rateToUsd),2) FROM User u " +
            "JOIN Wallet w ON u.id = w.holder.id " +
            "JOIN Transaction t ON t.senderWallet.id = w.id " +
            "JOIN Currency c ON t.receiverWallet.currency.id = c.id " +
            "WHERE u.id = :userId and t.status = 'SUCCESSFUL'")
    BigDecimal getSentAmountByUserId(int userId);

    @Query("SELECT ROUND(SUM(t.amount* c.rateToUsd),2) FROM User u " +
            "JOIN Wallet w ON u.id = w.holder.id " +
            "JOIN Transaction t ON t.receiverWallet.id = w.id " +
            "JOIN Currency c ON t.receiverWallet.currency.id = c.id " +
            "WHERE u.id = :userId and t.status = 'SUCCESSFUL'")
    BigDecimal getReceivedAmountByUserId(int userId);

}

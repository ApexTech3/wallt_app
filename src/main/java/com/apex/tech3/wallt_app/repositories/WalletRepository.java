package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Set;

public interface WalletRepository extends JpaRepository<Wallet, Integer>, JpaSpecificationExecutor<Wallet> {

       Wallet findByIdAndIsActiveTrue(int id);

       Set<Wallet> findByHolderId(int holderId);
       Set<Wallet> findByHolderIdAndIsActiveTrue(int holderId);

       @Query("SELECT ROUND(SUM(w.amount*w.currency.rateToUsd),2) FROM Wallet w WHERE w.holder.id = :userId AND w.isActive = true")
       BigDecimal getTotalBalance(int userId);
}

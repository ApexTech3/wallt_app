package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}

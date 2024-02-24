package com.apex.tech3.wallt_app.repositories.contracts;

import com.apex.tech3.wallt_app.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAll();

}

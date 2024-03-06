package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface WalletRepository extends JpaRepository<Wallet, Integer>, JpaSpecificationExecutor<Wallet> {

       Wallet findByIdAndIsActiveTrue(int id);

       Set<Wallet> findByHolderId(int holderId);
}

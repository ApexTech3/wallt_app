package com.apex.tech3.wallt_app.repositories.contracts;

import com.apex.tech3.wallt_app.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAll();

    Wallet getById(int id);

    @Query("from Wallet where (:holderId is null or holder.id= :holderId) and (:currencyId is null or currency.id = :currencyId)")
    List<Wallet> findByHolderAndCurrency(@Param("holderId") Integer holderId, @Param("currencyId") Integer currencyId);
}

package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    @Query("from Wallet where (:holderId is null or holder.id= :holderId) " +
            "and (:amountGreaterThan is null or amount >= :amountGreaterThan) " +
            "and (:amountLessThan is null or amount <= :amountLessThan) " +
            "and (:currencyId is null or currency.id = :currencyId)")
    Page<Wallet> findByHolderAndCurrency(@Param("holderId") Integer holderId,
                                         @Param("amountGreaterThan") BigDecimal amountGreaterThan,
                                         @Param("amountLessThan") BigDecimal amountLessThan,
                                         @Param("currencyId") Integer currencyId,
                                         Pageable pageable);
}

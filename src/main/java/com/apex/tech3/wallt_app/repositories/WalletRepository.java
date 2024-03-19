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

       Wallet findByHolderIdAndIsDefaultTrue(int holderId);

       Wallet findByHolderIdAndCurrencyId(int holderId, int currencyId);

       @Query("SELECT ROUND(SUM(w.amount*w.currency.rateToUsd),2) FROM Wallet w WHERE w.holder.id = :userId AND w.isActive = true")
       BigDecimal getTotalBalance(int userId);

       // To be implemented later
//       @Query(value = """
//               SELECT
//                   SUM(w.amount * c.rate_to_usd /
//                       (SELECT mc.rate_to_usd
//                        FROM wallt_db.currencies mc
//                        WHERE mc.currency_id =
//                           (SELECT currency_id
//                            FROM wallt_db.wallets w
//                            WHERE holder_id = :userId AND w.is_default = true)
//                       )
//                   )as total
//               FROM
//                   wallt_db.wallets w
//               JOIN
//                   wallt_db.currencies c ON w.currency_id = c.currency_id
//               WHERE
//                   holder_id = :userId AND w.is_active = true;""", nativeQuery = true)
//       BigDecimal getTotalBalanceInDefault(int userId);
}

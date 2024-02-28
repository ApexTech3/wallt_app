package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    @Query("from Wallet where (:#{#filterOptions.holderId} is null or holder.id= :#{#filterOptions.holderId}) " +
            "and (:#{#filterOptions.amountGreaterThan} is null or amount >= :#{#filterOptions.amountGreaterThan}) " +
            "and (:#{#filterOptions.amountLessThan} is null or amount <= :#{#filterOptions.amountLessThan}) " +
            "and (:#{#filterOptions.currencyId} is null or currency.id = :#{#filterOptions.currencyId})")
    Page<Wallet> findAllFilteredSortedAndPaginated(@Param("filterOptions") WalletFilterOptions filterOptions,
                                                   Pageable pageable);
}

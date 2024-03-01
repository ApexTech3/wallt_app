package com.apex.tech3.wallt_app.models.filters;

import com.apex.tech3.wallt_app.models.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> filterByAllColumns(Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, String currencySymbol, String status, Timestamp date) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(7);

            if(id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if(receiverWalletId != null) {
                predicates.add(criteriaBuilder.equal(root.get("receiverWallet").get("id"), receiverWalletId));
            }
            if(senderWalletId != null) {
                predicates.add(criteriaBuilder.equal(root.get("senderWallet").get("id"), senderWalletId));
            }
            if(amount != null) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), amount));
            }
            if(currencySymbol != null) {
                predicates.add(criteriaBuilder.equal(root.get("currency").get("symbol"), currencySymbol));
            }
            if(status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if(date != null) {
                predicates.add(criteriaBuilder.equal(root.get("stampCreated"), date));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

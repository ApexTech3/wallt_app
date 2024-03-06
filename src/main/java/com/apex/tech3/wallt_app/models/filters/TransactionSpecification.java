package com.apex.tech3.wallt_app.models.filters;

import com.apex.tech3.wallt_app.models.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {


    public static Specification<Transaction> filterBySenderAndStatus(Integer senderId, String status) {
        return (root, query, criteriaBuilder) -> {
            Predicate senderPredicate = criteriaBuilder.equal(root.get("senderWallet").get("id"), senderId);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
            return criteriaBuilder.and(senderPredicate, statusPredicate);
        };
    }

    public static Specification<Transaction> filterByReceiverAndStatus(Integer receiverId, String status) {
        return (root, query, criteriaBuilder) -> {
            Predicate receiverPredicate = criteriaBuilder.equal(root.get("receiverWallet").get("id"), receiverId);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
            return criteriaBuilder.and(receiverPredicate, statusPredicate);
        };
    }

    public static Specification<Transaction> filterByAllColumns(Integer id, Integer receiverWalletId, Integer senderWalletId, Double amount, Double amountGreaterThan, Double amountLesserThan, String status, LocalDate date, LocalDate laterThan, LocalDate earlierThan) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(10);

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
            if(amountGreaterThan != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amountGreaterThan));
            }
            if(amountLesserThan != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amountLesserThan));
            }
            if(status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if(date != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("DATE", java.sql.Date.class, root.get("stampCreated")), java.sql.Date.valueOf(date)));
            }
            if(laterThan != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("DATE", java.sql.Date.class, root.get("stampCreated")), java.sql.Date.valueOf(laterThan)));
            }
            if(earlierThan != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("DATE", java.sql.Date.class, root.get("stampCreated")), java.sql.Date.valueOf(earlierThan)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

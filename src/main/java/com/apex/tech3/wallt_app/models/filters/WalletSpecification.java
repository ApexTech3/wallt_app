package com.apex.tech3.wallt_app.models.filters;

import com.apex.tech3.wallt_app.models.Wallet;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WalletSpecification {
    public static Specification<Wallet> filterByAllColumns(Integer holderId, BigDecimal amountGreaterThan,
                                                           BigDecimal amountLessThan, Integer currencyId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (holderId != null)
                predicates.add(criteriaBuilder.equal(root.get("holder").get("id"), holderId));
            if (amountGreaterThan != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amountGreaterThan));
            if (amountLessThan != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amountLessThan));
            if (currencyId != null)
                predicates.add(criteriaBuilder.equal(root.get("currency").get("id"), currencyId));
            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

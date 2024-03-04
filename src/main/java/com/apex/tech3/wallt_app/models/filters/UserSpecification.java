package com.apex.tech3.wallt_app.models.filters;

import com.apex.tech3.wallt_app.models.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> filterByAllColumns(Integer id, String username, String firstName,
                                                         String middleName, String lastName, String email,
                                                         String phone) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(7);

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (username != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }
            if (firstName != null) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"), firstName));
            }
            if (middleName != null) {
                predicates.add(criteriaBuilder.equal(root.get("middleName"), middleName));
            }
            if (lastName != null) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), lastName));
            }
            if (email != null) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            if (phone != null) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

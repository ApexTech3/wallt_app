package com.apex.tech3.wallt_app.repositories;


import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    User getByEmail(String email);
    User findByConfirmationToken(String confirmationToken);

}

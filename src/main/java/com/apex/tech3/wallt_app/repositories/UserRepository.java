package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    User getByEmail(String email);
    User findByConfirmationToken(String confirmationToken);

}

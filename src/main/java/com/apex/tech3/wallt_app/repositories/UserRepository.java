package com.apex.tech3.wallt_app.repositories;


import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByPhone(String phoneNumber);
    User getByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    User findByUsernameAndEmail(String username, String email);


}

package com.apex.tech3.wallt_app.repositories.contracts;

import com.apex.tech3.wallt_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

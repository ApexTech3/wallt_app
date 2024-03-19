package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

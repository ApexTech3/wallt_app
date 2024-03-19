package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.models.Role;
import com.apex.tech3.wallt_app.repositories.RoleRepository;
import com.apex.tech3.wallt_app.services.contracts.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role get(String role) {
        return roleRepository.findByName(role);
    }

    @Override
    public List<Role> get() {
        return roleRepository.findAll();
    }
}

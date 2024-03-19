package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Role;

import java.util.List;

public interface RoleService {
    Role get(String role);

    List<Role> get();
}

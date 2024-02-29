package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.User;

import java.util.List;

public interface UserService {
    User get(int id);
    User get(String username);
    List<User> getAll();
    User register(User user);
    User update(User user, User requester, int id);
}

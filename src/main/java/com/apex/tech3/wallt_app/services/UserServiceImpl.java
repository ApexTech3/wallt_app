package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User get(int id) {
        return repository.getReferenceById(id);
    }

    @Override
    public User get(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User register(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }
        return repository.save(user);
    }
}

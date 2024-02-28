package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNAUTHORIZED_USER_ERROR = "You are not authorized to perform this operation";
    private final UserRepository repository;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserServiceImpl(UserRepository repository, AuthenticationHelper authenticationHelper) {
        this.repository = repository;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
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

    @Override
    public User update(User user, User requester) {
        tryAuthorizeUser(user, requester);
        checkIfUniqueEmail(user);
        return repository.update(user);
    }

    private void checkIfUniqueEmail(User user) {
        boolean duplicateExists = true;
        try {
            User userByEmail = repository.getByEmail(user.getEmail());
            if (userByEmail.getId() == user.getId())
                duplicateExists = false;
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
    }

    private User tryAuthorizeUser(User user, User requester) {
        if (!user.getUsername().equals(requester.getUsername()) && !AuthenticationHelper.isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        return user;
    }
}

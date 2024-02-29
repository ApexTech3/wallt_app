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

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
    public User update(User user, User requester, int id) {
        User userToUpdate = repository.getById(id);
        User updatedUser = addUneditableAttributes(userToUpdate, user);
        user.setStampCreated(userToUpdate.getStampCreated());
        tryAuthorizeUser(requester, userToUpdate);
        checkIfUniqueEmail(updatedUser);
        return repository.save(updatedUser);
    }

    private void checkIfUniqueEmail(User user) {
        boolean duplicateExists = true;
        User userByEmail = repository.getByEmail(user.getEmail());
        if (userByEmail == null) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
    }

    private User tryAuthorizeUser(User user, User requester) {
        if (!user.getUsername().equals(requester.getUsername()) && !AuthenticationHelper.isBlocked(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        return user;
    }

    private User addUneditableAttributes(User old, User updated) {
        updated.setBlocked(old.isBlocked());
        updated.setVerified(old.isVerified());
        updated.setStampCreated(old.getStampCreated());
        return updated;
    }
}

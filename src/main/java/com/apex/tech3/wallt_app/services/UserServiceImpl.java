package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InvalidPasswordException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
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
    public User update(User user, User requester, UserUpdateDto dto, int id) {
        User userToUpdate = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        tryAuthorizeUser(requester, userToUpdate);
        checkIfUniqueEmail(user);
        User updatedUser = addUneditableAttributes(userToUpdate, user);
        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            updatedUser = validateNewPassword(dto, updatedUser);
        } else {
            updatedUser.setPassword(requester.getPassword());
        }
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

    private void tryAuthorizeUser(User user, User requester) {
        if (!user.getUsername().equals(requester.getUsername()) && !AuthenticationHelper.isBlocked(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
    }

    private User addUneditableAttributes(User old, User updated) {
        updated.setBlocked(old.isBlocked());
        updated.setVerified(old.isVerified());
        updated.setStampCreated(old.getStampCreated());
        updated.setDeleted(false);
        return updated;
    }

    private User validateNewPassword(UserUpdateDto toUpdate, User updated) {
        String newPassword = toUpdate.getNewPassword();
        String passwordConfirmation = toUpdate.getPasswordConfirmation();

        if (newPassword.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[+\\-*&^]).{8,}$")
                && newPassword.equals(passwordConfirmation)) {
            updated.setPassword(newPassword);
            return updated;
        } else {
            throw new InvalidPasswordException("Invalid new password. Please ensure it meets the following criteria: "
                    + "at least 8 characters long, contains at least one uppercase letter, one digit, and one special character.");
        }
    }

    @Override
    public boolean isAdmin(User admin) {
        return admin.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"));
    }

    @Override
    public User blockUser(int userId, User requester) {
        if(!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeBlocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));

        userToBeBlocked.setBlocked(true);
        return repository.save(userToBeBlocked);
    }

    @Override
    public User unblockUser(int userId, User requester) {
        if(!isAdmin(requester)) {
            throw new AuthorizationException(UNAUTHORIZED_USER_ERROR);
        }
        User userToBeUnblocked = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));
        userToBeUnblocked.setBlocked(false);
        return repository.save(userToBeUnblocked);
    }
}

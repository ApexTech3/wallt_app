package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getById(int id);

    User getByUsername(String username);
    Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                             String middleName, String lastName, String email,
                             String phone);
    User register(User user);

    void sendConfirmationEmail(User user);

    void confirmUser(String token);
    User update(User user, User requester, UserUpdateDto dto, int id);

    boolean isAdmin(User user);

    User blockUser(int userId, User requester);

    User unblockUser(int userId, User requester);

    void deleteUser(int userId, User requester);
    User restoreUser(int userId, User requester);
}

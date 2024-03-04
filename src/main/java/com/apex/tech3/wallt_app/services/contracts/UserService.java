package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    User get(int id);
    User get(String username);
    Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                             String middleName, String lastName, String email,
                             String phone);
    User register(User user);
    public void sendConfirmationEmail(User user);
    public void confirmUser(String token);
    User update(User user, User requester, UserUpdateDto dto, int id);

    boolean isAdmin(User user);

    User blockUser(int userId, User requester);

    User unblockUser(int userId, User requester);

    void deleteUser(int userId, User requester);
    User restoreUser(int userId, User requester);
}

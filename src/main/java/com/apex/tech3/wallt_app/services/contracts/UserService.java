package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.AdminFinancialActivity;
import com.apex.tech3.wallt_app.models.FinancialActivity;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getById(int id);

    User getByToken(String token);

    User getByUsername(String username);

    Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                      String middleName, String lastName, String email,
                      String phone);

    List<User> getAllActiveAndVerified();

    User register(User user);

    void sendConfirmationEmail(User user);

    void confirmUser(String token);

    public void confirmResetPasswordToken(String token);

    void handleForgottenPassword(String username, String email);

    void changePassword(PasswordRecoveryDto dto);

    User update(User user, User requester);

    boolean isAdmin(User user);

    User blockUser(int userId, User requester);

    User unblockUser(int userId, User requester);

    void deleteUser(int userId, User requester);

    User restoreUser(int userId, User requester);

    List<FinancialActivity> collectActivity(int userId);

    List<AdminFinancialActivity> collectAllActivity();

    public Map<String, Object> collectActivityAndStats(int userId);
}

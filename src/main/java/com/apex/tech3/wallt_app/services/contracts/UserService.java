package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.FinancialActivity;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    User getById(int id);

    User getByToken(String token);

    User getByUsername(String username);

    Page<User> getAll(Pageable pageable, Integer id, String username, String firstName,
                      String middleName, String lastName, String email,
                      String phone);

    User register(User user);

    void sendConfirmationEmail(User user);

    void confirmUser(String token);

    public void confirmResetPasswordToken(String token);

    void handleForgottenPassword(String username, String email);

    void changePassword(PasswordRecoveryDto dto);

    User update(User user, User requester, UserUpdateDto dto, int id);

    boolean isAdmin(User user);

    User blockUser(int userId, User requester);

    User unblockUser(int userId, User requester);

    void deleteUser(int userId, User requester);

    User restoreUser(int userId, User requester);

    List<FinancialActivity> collectActivity(int userId);

    public Map<String, Object> collectActivityAndStats(int userId);
}

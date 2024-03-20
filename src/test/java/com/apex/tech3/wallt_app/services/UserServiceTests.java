package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.*;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.utils.EmailServiceImpl;
import com.apex.tech3.wallt_app.helpers.utils.TokenService;
import com.apex.tech3.wallt_app.models.Role;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.filters.UserSpecification;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository mockRepository;
    @Mock
    AuthenticationHelper authenticationHelper;
    @Mock
    TokenService tokenService;
    @Mock
    EmailServiceImpl emailService;
    @Mock
    CurrencyService currencyService;
    @Mock
    WalletService walletService;
    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockUser));
        userService.getById(1);

        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    public void getByToken_Should_CallRepository_When_MethodCalled() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByConfirmationToken("a")).thenReturn(mockUser);

        userService.getByToken("a");

        Mockito.verify(mockRepository, times(1)).findByConfirmationToken("a");
    }

    @Test
    public void getByUsername_Should_CallRepository_When_MethodCalled() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByUsername("MockUsername")).thenReturn(mockUser);

        userService.getByUsername("MockUsername");

        Mockito.verify(mockRepository, times(1)).findByUsername("MockUsername");
    }


    @Test
    public void getAllActiveANdVerified_Should_CallRepository_When_MethodCalled() {
        List<User> users = new ArrayList<>();
        Mockito.when(mockRepository.findAll(UserSpecification.getAllActiveAndVerified())).thenReturn(users);

        userService.getAllActiveAndVerified();

        Mockito.verify(mockRepository, times(1)).findAll(UserSpecification.getAllActiveAndVerified());
    }

    @Test
    public void register_Should_ThrowException_When_UserAlreadyExists() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.existsByUsername(mockUser.getUsername())).thenReturn(true);

        Assertions.assertThrows(EntityDuplicateException.class, () -> userService.register(mockUser));
    }

    @Test
    public void confirmResetPasswordToken_Should_ThrowEntityNotFoundException_When_UserNotFound() {
        Mockito.when(mockRepository.findByConfirmationToken(anyString())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.confirmResetPasswordToken("invalidToken"));

        Mockito.verify(mockRepository, never()).save(any());
    }

    @Test
    public void handleForgottenPassword_Should_ThrowEntityNotFoundException_When_UserNotFound() {
        Mockito.when(mockRepository.findByUsernameAndEmail(anyString(),anyString())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.handleForgottenPassword("test", "test"));

        Mockito.verify(mockRepository, never()).save(any());
        Mockito.verify(mockRepository, never()).save(any());
    }


    @Test
    public void changePassword_WhenPasswordsMatch_ShouldChangePassword() {
        PasswordRecoveryDto dto = new PasswordRecoveryDto();
        dto.setPassword("newPassword");
        dto.setPasswordConfirmation("newPassword");
        dto.setToken("validToken");
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByConfirmationToken(dto.getToken())).thenReturn(mockUser);

        userService.changePassword(dto);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void changePassword_WhenPasswordsDoNotMatch_ShouldThrowException() {
        PasswordRecoveryDto dto = new PasswordRecoveryDto();
        dto.setPassword("newPassword");
        dto.setPasswordConfirmation("wrongPassword");

        Assertions.assertThrows(InvalidPasswordException.class, () -> userService.changePassword(dto));
        Mockito.verify(mockRepository, Mockito.never()).findByConfirmationToken(anyString());
        Mockito.verify(mockRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    public void addUneditableAttributes_ShouldSetPropertiesBasedOnOldUser() {
        User oldUser = Helpers.createMockUser();
        User updatedUser = new User();
        updatedUser.setId(2);
        updatedUser.setUsername("UpdatedUsername");
        updatedUser.setPassword("UpdatedPassword");
        updatedUser.setFirstName("UpdatedFirstName");
        updatedUser.setLastName("UpdatedLastName");
        updatedUser.setEmail("updated@user.com");
        updatedUser.setRoles(oldUser.getRoles());
        updatedUser.setBlocked(true);
        updatedUser.setVerified(false);
        updatedUser.setStampCreated(new Timestamp(System.currentTimeMillis()));
        updatedUser.setDeleted(true);

        User result = userService.addUneditableAttributes(oldUser, updatedUser);

        Assertions.assertEquals(oldUser.isBlocked(), result.isBlocked());
        Assertions.assertEquals(oldUser.isVerified(), result.isVerified());
        Assertions.assertEquals(oldUser.getStampCreated(), result.getStampCreated());
        Assertions.assertFalse(result.isDeleted());
        Assertions.assertEquals(updatedUser.getId(), result.getId());
        Assertions.assertEquals(updatedUser.getUsername(), result.getUsername());
        Assertions.assertEquals(updatedUser.getPassword(), result.getPassword());
        Assertions.assertEquals(updatedUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(updatedUser.getLastName(), result.getLastName());
        Assertions.assertEquals(updatedUser.getEmail(), result.getEmail());
        Assertions.assertEquals(updatedUser.getRoles(), result.getRoles());
    }

    @Test
    public void validateNewPassword_WithValidPassword_ShouldReturnUpdatedUser() {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setNewPassword("ValidP-assword1!");
        updateDto.setPasswordConfirmation("ValidP-assword1!");

        User updatedUser = Helpers.createMockUser();

        User result = userService.validateNewPassword(updateDto, updatedUser);

        Assertions.assertEquals(updateDto.getNewPassword(), result.getPassword());
        Assertions.assertSame(updatedUser, result);
    }

    @Test
    public void validateNewPassword_WithInvalidPassword_ShouldThrowInvalidPasswordException() {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setNewPassword("invalid");

        User updatedUser = Helpers.createMockUser();

        Assertions.assertThrows(InvalidPasswordException.class, () -> userService.validateNewPassword(updateDto, updatedUser));
    }

    @Test
    public void isAdmin_WithAdminUser_ShouldReturnTrue() {
        User adminUser = Helpers.createMockAdmin();
        Role adminRole = Helpers.createMockAdminRole();
        adminUser.setRoles(Set.of(adminRole));

        boolean result = userService.isAdmin(adminUser);

        Assertions.assertTrue(result);
    }

    @Test
    public void isAdmin_WithRegularUser_ShouldReturnFalse() {
        User regularUser = Helpers.createMockUser();
        Role userRole = Helpers.createMockUserRole();
        regularUser.setRoles(Set.of(userRole));

        boolean result = userService.isAdmin(regularUser);

        Assertions.assertFalse(result);
    }

    @Test
    public void unblockUser_WithAdminUser_ShouldUnblockUser() {
        int userId = 123;
        User adminUser = Helpers.createMockAdmin();
        User userToBeUnblocked = Helpers.createMockUser();
        userToBeUnblocked.setBlocked(true);

        Mockito.when(mockRepository.findById(userId)).thenReturn(java.util.Optional.of(userToBeUnblocked));
        Mockito.when(mockRepository.save(userToBeUnblocked)).thenReturn(userToBeUnblocked);

        User result = userService.unblockUser(userId, adminUser);

        Assertions.assertFalse(result.isBlocked());
        Mockito.verify(mockRepository, times(1)).findById(userId);
        Mockito.verify(mockRepository, times(1)).save(userToBeUnblocked);
    }

    @Test
    public void unblockUser_WithNonAdminUser_ShouldThrowAuthorizationException() {
        int userId = 123;
        User regularUser = Helpers.createMockUser();
        AuthorizationException exception = Assertions.assertThrows(AuthorizationException.class, () -> userService.unblockUser(userId, regularUser));
        Assertions.assertEquals("You are not authorized to perform this operation", exception.getMessage());
        Mockito.verify(mockRepository, never()).findById(anyInt());
        Mockito.verify(mockRepository, never()).save(any());
    }

    @Test
    public void blockUser_WithAdminUser_ShouldBlockUser() {
        int userId = 1;
        User requester = Helpers.createMockAdmin();
        User userToBeBlocked = Helpers.createMockUser();

        Mockito.when(mockRepository.findById(userId)).thenReturn(Optional.of(userToBeBlocked));
        Mockito.when(mockRepository.save(userToBeBlocked)).thenReturn(userToBeBlocked);

        User result = userService.blockUser(userId, requester);

        Assertions.assertTrue(result.isBlocked());
        Assertions.assertEquals(userToBeBlocked, result);
    }

    @Test
    public void blockUser_WithNonAdminUser_ShouldThrowAuthorizationException() {
        int userId = 1;
        User requester = Helpers.createMockUser();
        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.blockUser(userId, requester);
        });
        Mockito.verify(mockRepository, never()).findById(userId);
        Mockito.verify(mockRepository, never()).save(any());
    }

    @Test
    public void getAll_Should_CallRepositoryWhenMethodCalled() {
        userService.getAll(null, null, null, null, null, null, null, null);
        Mockito.verify(mockRepository, times(1)).findAll(Mockito.any(Specification.class));
    }

    @Test
    public void getAllActiveAndVerified_Should_CallRepositoryWhenMethodCalled() {
        userService.getAllActiveAndVerified();
        Mockito.verify(mockRepository, times(1)).findAll(Mockito.any(Specification.class));
    }

    @Test
    public void register_Should_CallRepository_When_ValidUserSubmitted() {
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.existsByUsername(user.getUsername())).thenReturn(false);
        Mockito.when(mockRepository.existsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(mockRepository.existsByPhone(user.getPhone())).thenReturn(false);
        Mockito.when(tokenService.generateToken()).thenReturn("mockToken");
        Mockito.when(currencyService.getById(2)).thenReturn(Helpers.createMockCurrency());
        userService.register(user);
        Mockito.verify(mockRepository, times(1)).save(user);
    }

    @Test
    public void register_Should_Throw_When_UsernameExists() {
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.existsByUsername(user.getUsername())).thenReturn(true);
        Assertions.assertThrows(EntityDuplicateException.class, () -> userService.register(user));
    }

    @Test
    public void register_Should_Throw_When_EmailExists() {
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.existsByUsername(user.getUsername())).thenReturn(false);
        Mockito.when(mockRepository.existsByEmail(user.getEmail())).thenReturn(true);
        Assertions.assertThrows(EntityDuplicateException.class, () -> userService.register(user));
    }

    @Test
    public void register_Should_Throw_When_phoneExists() {
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.existsByUsername(user.getUsername())).thenReturn(false);
        Mockito.when(mockRepository.existsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(mockRepository.existsByPhone(user.getPhone())).thenReturn(true);
        Assertions.assertThrows(EntityDuplicateException.class, () -> userService.register(user));
    }

    @Test
    public void update_Should_Throw_When_UserIsNotTheRequester() {
        User mockUser = Helpers.createMockUser();
        User mockUser2 = Helpers.createMockUser();
        mockUser2.setUsername("DifferentMockUsername");

        Assertions.assertThrows(AuthorizationException.class, () -> userService.update(mockUser, mockUser2));
    }

    @Test
    public void update_Should_Throw_When_EmailIsNotUnique() {
        User mockUser = Helpers.createMockUser();
        User mockUser2 = Helpers.createMockUser();
        mockUser2.setId(2);
        Mockito.when(mockRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser2);

        Assertions.assertThrows(EntityDuplicateException.class, () -> userService.update(mockUser, mockUser));
    }

    @Test
    public void update_Should_CallRepository_When_UserIsAuthorizedAndEmailIsSame() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser);

        userService.update(mockUser, mockUser);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void confirmUser_Should_CallRepository_When_TokenIsValid() {
        User mockUser = Helpers.createMockUser();
        mockUser.setConfirmationToken("mockToken");
        Mockito.when(mockRepository.findByConfirmationToken("mockToken")).thenReturn(mockUser);
        Mockito.when(tokenService.isValidToken("mockToken")).thenReturn(true);
        userService.confirmUser("mockToken");
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void confirmUser_Should_Throw_When_UserNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.confirmUser("mockToken"));
    }

    @Test
    public void confirmUser_Should_Throw_When_InvalidToken() {
        User mockUser = Helpers.createMockUser();
        mockUser.setConfirmationToken("mockToken");
        Mockito.when(mockRepository.findByConfirmationToken("mockToken")).thenReturn(mockUser);
        Mockito.when(tokenService.isValidToken("mockToken")).thenReturn(false);
        Assertions.assertThrows(InvalidTokenException.class, () -> userService.confirmUser("mockToken"));
    }

    @Test
    public void confirmResetPasswordToken_Should_Throw_When_UserNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.confirmResetPasswordToken("mockToken"));
    }

    @Test
    public void confirmResetPasswordToken_Should_Throw_When_InvalidToken() {
        User mockUser = Helpers.createMockUser();
        mockUser.setConfirmationToken("mockToken");
        Mockito.when(mockRepository.findByConfirmationToken("mockToken")).thenReturn(mockUser);
        Mockito.when(tokenService.isValidToken("mockToken")).thenReturn(false);
        Assertions.assertThrows(InvalidTokenException.class, () -> userService.confirmResetPasswordToken("mockToken"));
    }

    @Test
    public void confirmResetPasswordToken_Should_CallRepository_When_ValidToken() {
        User mockUser = Helpers.createMockUser();
        mockUser.setConfirmationToken("mockToken");
        Mockito.when(mockRepository.findByConfirmationToken("mockToken")).thenReturn(mockUser);
        Mockito.when(tokenService.isValidToken("mockToken")).thenReturn(true);
        userService.confirmResetPasswordToken("mockToken");
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void handleForgottenPassword_Should_CallRepository_When_ValidToken() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByUsernameAndEmail(Mockito.any(),Mockito.any())).thenReturn(mockUser);
        Mockito.when(tokenService.generateToken()).thenReturn("mockToken");
        userService.handleForgottenPassword("mockUsername", "mockEmail");
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void handleForgottenPassword_Should_Throw_When_InvalidUser() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.handleForgottenPassword("mockUsername", "mockEmail"));
    }

    @Test
    public void changePassword_Should_CallRepository_When_ValidPassword() {
        PasswordRecoveryDto mockPasswordRecoveryDto = Helpers.createPasswordRecoveryDto();
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByConfirmationToken("mockToken")).thenReturn(mockUser);
        userService.changePassword(mockPasswordRecoveryDto);
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void changePassword_Should_Throw_When__InvalidPassword() {
        PasswordRecoveryDto mockPasswordRecoveryDto = Helpers.createPasswordRecoveryDto();
        mockPasswordRecoveryDto.setPasswordConfirmation("invalidPassword");
        Assertions.assertThrows(InvalidPasswordException.class, () -> userService.changePassword(mockPasswordRecoveryDto));
    }

    @Test
    public void switchBlockedStatus_Should_CallRepository_When_ValidUser() {
        User mockUser = Helpers.createMockUser();
        User mockAdmin = Helpers.createMockAdmin();
        Mockito.when(mockRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));
        userService.switchBlockedStatus(1, mockAdmin);
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void switchBlockedStatus_Should_Throw_When_RequesterNotAdmin() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));
        Assertions.assertThrows(AuthorizationException.class, () -> userService.switchBlockedStatus(1, mockUser));
    }

    @Test
    public void switchBlockedStatus_Should_Throw_When_UserNotFound() {
        User mockUser = Helpers.createMockUser();
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.switchBlockedStatus(1, mockUser));
    }

    @Test
    public void deleteUser_Should_CallRepository_When_ValidUser() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));
        userService.deleteUser(1, mockUser);
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void deleteUser_Should_Throw_When_UserNotRequesterOrAdmin() {
        User mockUser = Helpers.createMockUser();
        Assertions.assertThrows(AuthorizationException.class, () -> userService.deleteUser(2, mockUser));
    }

    @Test
    public void deleteUser_Should_Throw_When_UserNotFound() {
        User mockUser = Helpers.createMockUser();
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1, mockUser));
    }

    @Test
    public void restoreUser_Should_CallRepository_When_ValidUser() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));
        userService.restoreUser(1, mockUser);
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

    @Test
    public void restoreUser_Should_Throw_When_UserNotRequesterOrAdmin() {
        User mockUser = Helpers.createMockUser();
        Assertions.assertThrows(AuthorizationException.class, () -> userService.restoreUser(2, mockUser));
    }

    @Test
    public void restoreUser_Should_Throw_When_UserNotFound() {
        User mockUser = Helpers.createMockUser();
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.restoreUser(1, mockUser));
    }

}
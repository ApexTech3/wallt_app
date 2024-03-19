package com.apex.tech3.wallt_app.services;
import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.*;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TokenService;
import com.apex.tech3.wallt_app.models.Role;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.filters.UserSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.apex.tech3.wallt_app.repositories.UserRepository;

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
    @InjectMocks
    TokenService tokenService;
    @InjectMocks
    EmailServiceImpl emailService;
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
        // Mock the behavior of the repository method findByConfirmationToken to return null
        Mockito.when(mockRepository.findByConfirmationToken(anyString())).thenReturn(null);

        // Call the method under test and assert that it throws EntityNotFoundException
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.confirmResetPasswordToken("invalidToken"));

        // Verify that repository.save() is not called
        Mockito.verify(mockRepository, never()).save(any());
    }

    @Test
    public void handleForgottenPassword_Should_ThrowEntityNotFoundException_When_UserNotFound() {
        Mockito.when(mockRepository.getByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.handleForgottenPassword("test", "test"));

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
        //Mockito.when(userService.isAdmin(adminUser)).thenReturn(true);
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
        //Mockito.when(userService.isAdmin(regularUser)).thenReturn(false);
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
        //Mockito.when(userService.isAdmin(requester)).thenReturn(true);

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
        //Mockito.when(userService.isAdmin(requester)).thenReturn(false);
        Assertions.assertThrows(AuthorizationException.class, () -> {
            userService.blockUser(userId, requester);
        });
        Mockito.verify(mockRepository, never()).findById(userId);
        Mockito.verify(mockRepository, never()).save(any());
    }



}

package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InvalidTokenException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.UserMapper;
import com.apex.tech3.wallt_app.helpers.utils.CloudinaryUploadService;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.PasswordRecoveryDto;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Login;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Register;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final CloudinaryUploadService cloudinaryUploadService;

    public AuthenticationController(AuthenticationHelper authenticationHelper, UserMapper userMapper, UserService userService, CloudinaryUploadService cloudinaryUploadService) {
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }


    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model) {
        boolean isAuthenticated = httpSession.getAttribute("currentUser") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);

        model.addAttribute("isAdmin", isAuthenticated ? httpSession.getAttribute("isAdmin") : false);
        model.addAttribute("isBlocked", isAuthenticated ? httpSession.getAttribute("isBlocked") : false);
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new UserRegisterDto());
        return "authentication-login";
    }

    @PostMapping("/login")
    public String handleLogin(@Validated(Login.class) @ModelAttribute("login") UserRegisterDto dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "authentication-login";
        }

        try {
            User user = authenticationHelper.verifyAuthentication(dto.getUsername(), dto.getPassword());
            session.setAttribute("currentUser", dto.getUsername());
            session.setAttribute("isAdmin", AuthenticationHelper.isAdmin(user));
            session.setAttribute("isBlocked", AuthenticationHelper.isBlocked(user));
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        } catch (AuthenticationFailureException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "authentication-login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new UserRegisterDto());
        return "authentication-register";
    }

    @PostMapping("/register")
    public String handleRegister(@Validated(Register.class) @ModelAttribute("register") UserRegisterDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authentication-register";
        }
        try {
            if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
                bindingResult.rejectValue("password", "error", "Passwords do not match");
                return "authentication-register";
            }
            User user = userMapper.fromRegisterDto(dto);
            MultipartFile profilePicture = dto.getProfilePicture();
            if (!profilePicture.isEmpty()) {
                File pictureFile = File.createTempFile("temp", profilePicture.getOriginalFilename());
                profilePicture.transferTo(pictureFile);
                String pictureUrl = cloudinaryUploadService.uploadImage(pictureFile);
                user.setProfilePicture(pictureUrl);
            }

            userService.register(user);
            return "redirect:/auth/login";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "authentication-register";
        } catch (IOException e) {
            bindingResult.rejectValue("profilePicture", "auth_error", e.getMessage());
            return "authentication-register";
        }
    }

    @PostMapping("/forgotten")
    public String handleForgottenPasswordRequest(@RequestParam("username") String username,
                                                                 @RequestParam("email") String email) {
        try {
            userService.handleForgottenPassword(username, email);
        } catch (EntityNotFoundException e) {
            return "password-reset-failure";
        }
        String successMessage = "Password reset email sent successfully.";
        return "password-reset-success";
    }

    @GetMapping("/passwordReset")
    public String confirmPasswordResetToken(@RequestParam("token") String token) {
        try {
            userService.confirmResetPasswordToken(token);
        } catch (EntityNotFoundException | InvalidTokenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "redirect:/auth/reset?token=" + token;
    }

    @GetMapping("/reset")
    public String showForgottenPasswordPage(@RequestParam("token") String token, Model model) {
        PasswordRecoveryDto dto = new PasswordRecoveryDto();
        dto.setToken(token);
        model.addAttribute("password", dto);
        return "authentication-password-reset";
    }

    @PostMapping("/reset")
    public String handleReset(@Validated @ModelAttribute("password") PasswordRecoveryDto dto, @RequestParam("token") String token, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authentication-password-reset";
        }
        dto.setToken(token);
        userService.changePassword(dto);
        return "redirect:/auth/login";
    }
}

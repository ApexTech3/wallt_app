package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.UserMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.services.CloudinaryUploadService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
    public String handleLogin(@Validated(UserRegisterDto.class) @ModelAttribute("login") UserRegisterDto dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "authentication-login";
        }

        try {
            User user = authenticationHelper.verifyAuthentication(dto.getUsername(), dto.getPassword());
            session.setAttribute("currentUser", dto.getUsername());
            session.setAttribute("isAdmin", AuthenticationHelper.isAdmin(user));
            session.setAttribute("isBlocked", AuthenticationHelper.isBlocked(user));
            session.setAttribute("userId", user.getId());
            return "redirect:/";
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
    public String handleRegister(@Validated @ModelAttribute("register") UserRegisterDto dto, BindingResult bindingResult) {
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
            //email error
            return "authentication-register";
        } catch (IOException e) {
            bindingResult.rejectValue("profilePicture", "auth_error", e.getMessage());
            return "authentication-register";
        }
    }

}

package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.UserMapper;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.dtos.*;
import com.apex.tech3.wallt_app.services.CloudinaryUploadService;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final CardService cardService;
    private final WalletService walletService;
    private final UserMapper mapper;
    private final CloudinaryUploadService cloudinaryUploadService;

    @Autowired
    public UserController(UserService userService, AuthenticationHelper authenticationHelper, CardService cardService, WalletService walletService, UserMapper mapper, CloudinaryUploadService cloudinaryUploadService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.cardService = cardService;
        this.walletService = walletService;
        this.mapper = mapper;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }

    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model, HttpServletRequest request) {
        boolean isAuthenticated = httpSession.getAttribute("currentUser") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAuthenticated ? httpSession.getAttribute("isAdmin") : false);
        model.addAttribute("isBlocked", isAuthenticated ? httpSession.getAttribute("isBlocked") : false);

        model.addAttribute("requestURI", request.getRequestURI());

        model.addAttribute("cardDto", new CardDto());
        model.addAttribute("walletDto", new WalletDto());
        model.addAttribute("transferDto", new TransferDto());
        model.addAttribute("transactionDto", new TransactionDto());
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            UserUpdateDto dto = mapper.toUpdateDto(userService.getById(id));
            model.addAttribute("user", dto);
            model.addAttribute("adminOrCurrentUser", user.getId() == id || AuthenticationHelper.isAdmin(userService.getById(user.getId())));
            try {
                Set<Card> userCards = cardService.getByHolderId(id);
                List<Wallet> userWallets = walletService.getByUserId(id);
                model.addAttribute("userCards", userCards);
                model.addAttribute("userWallets", userWallets);
            } catch (EntityNotFoundException e) {
                model.addAttribute("userCards", null);
            }
            return "user-view";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        }
    }

    @PostMapping("/{id}/edit")
    public String editUser(@PathVariable int id, @Valid @ModelAttribute("user") UserUpdateDto userDto,
                           BindingResult bindingResult, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
            tryAuthenticateUser(id, user);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/users/{id}";
        }
        if (!userDto.getCurrentPassword().equals(user.getPassword())) {
            bindingResult.rejectValue("currentPassword", "error", "Invalid password");
            return "redirect:/users/{id}";
        }

        if (!userDto.getNewPassword().isEmpty() && userDto.getNewPassword().equals(userDto.getPasswordConfirmation())) {
            userDto.setCurrentPassword(userDto.getNewPassword());
        } else if (!userDto.getNewPassword().isEmpty()) {
            bindingResult.rejectValue("newPassword", "error", "Passwords do not match");
            return "redirect:/users/{id}";
        }
        try {
            MultipartFile profilePicture = userDto.getProfilePicture();
            if (!profilePicture.isEmpty()) {
                File pictureFile = File.createTempFile("temp", profilePicture.getOriginalFilename());
                profilePicture.transferTo(pictureFile);
                String pictureUrl = cloudinaryUploadService.uploadImage(pictureFile);
                userDto.setProfilePictureURL(pictureUrl);
            }
            userService.update(mapper.fromUpdateDto(userDto, id), user);
            return "redirect:/users/{id}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("email", "error", e.getMessage());
            return "redirect:/users/{id}";
        } catch (IOException e) {
            bindingResult.rejectValue("profilePicture", "auth_error", e.getMessage());
            return "redirect:/users/{id}";
        }
    }

    private void tryAuthenticateUser(int id, User user) {
        if (!AuthenticationHelper.isAdmin(user) && user.getId() != id) {
            throw new AuthorizationException("You are not allowed to perform this operation");
        }
    }
}



package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final CardService cardService;
    private final WalletService walletService;

    @Autowired
    public UserController(UserService userService, AuthenticationHelper authenticationHelper, CardService cardService, WalletService walletService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.cardService = cardService;
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("user", userService.getById(id));
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

}



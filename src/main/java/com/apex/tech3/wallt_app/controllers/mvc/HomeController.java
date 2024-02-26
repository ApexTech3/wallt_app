package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.Attribute;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    private final CardService cardService;


    private final WalletService walletService;

    public HomeController(UserService userService, CardService cardService, WalletService walletService) {
        this.userService = userService;
        this.cardService = cardService;
        this.walletService = walletService;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("wallet", walletService.get(4));
        return "index";
    }

    @GetMapping("/{id}")
    public String getSingleUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "user";
    }

    @GetMapping("/login")
    public String loginBasic(Model model) {
        model.addAttribute("user", new User());
        return "auth-login-basic";
    }

    @PostMapping("/login")
    public String loginBasic(@Valid @ModelAttribute("user") User user, Model model) {
        return "user";
    }
}



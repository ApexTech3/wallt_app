package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.models.dtos.CardDto;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model) {
        boolean isAuthenticated = httpSession.getAttribute("currentUser") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);

        model.addAttribute("isAdmin", isAuthenticated ? httpSession.getAttribute("isAdmin") : false);
        model.addAttribute("isBlocked", isAuthenticated ? httpSession.getAttribute("isBlocked") : false);
        model.addAttribute("cardDto", new CardDto());
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getById(4));
        model.addAttribute("wallet", walletService.getById(4));
        model.addAttribute("demoActivity", userService.collectActivity(1)); //TODO Pick a user for demonstration
        model.addAttribute("stats", userService.collectStats(1)); //TODO Pick a user for demonstration
        return "index";
    }

    @GetMapping("/{id}")
    public String getSingleUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }

//    @GetMapping("/login")
//    public String loginBasic(Model model) {
//        model.addAttribute("user", new User());
//        return "html/signin";
//    }
//
//    @PostMapping("/login")
//    public String loginBasic(@Valid @ModelAttribute("user") User user, Model model) {
//        return "user";
//    }
}



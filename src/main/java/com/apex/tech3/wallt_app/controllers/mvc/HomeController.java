package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Wallet;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    private final CardService cardService;

    private final WalletService walletService;

    private final WalletMapper walletMapper;

    public HomeController(UserService userService, CardService cardService, WalletService walletService, WalletMapper walletMapper) {
        this.userService = userService;
        this.cardService = cardService;
        this.walletService = walletService;
        this.walletMapper = walletMapper;
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
        int userId = 3; //toDO remove hardcoded value for dashboard
        int userId2 = 4; //toDO remove hardcoded value for dashboard
        model.addAttribute("users", userService.getById(userId2));
        model.addAttribute("wallet", walletService.getById(userId2));
        model.addAttribute("demoActivity", userService.collectActivity(3));
        model.addAttribute("cards", cardService.getByHolderId(userId));
        model.addAttribute("stats", userService.collectStats(userId));
        List<Wallet> wallets = walletService.getByUserId(userId);
        model.addAttribute("wallets", wallets.stream().map(walletMapper::toDto));
        model.addAttribute("walletAmounts", wallets.stream().map(Wallet::getAmount).collect(Collectors.toList()));
        model.addAttribute("walletSymbols", wallets.stream().map(Wallet::getCurrency).map(Currency::getTicker).collect(Collectors.toList()));
        model.addAttribute("walletsTotal", walletService.getTotalBalance(userId));
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



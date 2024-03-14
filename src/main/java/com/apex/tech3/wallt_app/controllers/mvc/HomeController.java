package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    private final CardService cardService;

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    public HomeController(UserService userService, CardService cardService, WalletService walletService, CurrencyService currencyService, WalletMapper walletMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.cardService = cardService;
        this.walletService = walletService;
        this.walletMapper = walletMapper;
    }

    @GetMapping()
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getAll(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") != null)
            return "redirect:/dashboard";
        int userId = 3;
        List<WalletDto> wallets = walletService.getByUserId(3).stream().map(walletMapper::toDto).toList();
        model.addAttribute("activityAndStats", userService.collectActivityAndStats(userId));
        model.addAttribute("wallets", wallets);
        model.addAttribute("walletsTotal", walletService.getTotalBalance(userId));
        return "index";
    }

}



package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.dtos.CardDto;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;

    private final CardService cardService;

    private final WalletService walletService;
    private final CurrencyService currencyService;
    private final WalletMapper walletMapper;
    private final AuthenticationHelper authenticationHelper;

    public DashboardController(UserService userService, CardService cardService, WalletService walletService, CurrencyService currencyService, WalletMapper walletMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.cardService = cardService;
        this.walletService = walletService;
        this.currencyService = currencyService;
        this.walletMapper = walletMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model) {
        boolean isAuthenticated = httpSession.getAttribute("currentUser") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);

        model.addAttribute("isAdmin", isAuthenticated ? httpSession.getAttribute("isAdmin") : false);
        model.addAttribute("isBlocked", isAuthenticated ? httpSession.getAttribute("isBlocked") : false);
        model.addAttribute("cardDto", new CardDto());
        model.addAttribute("walletDto", new WalletDto());
        model.addAttribute("transferDto", new TransferDto());
    }

    @GetMapping("")
    public String getLoggedInPage(Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            List<Wallet> wallets = walletService.getByUserId(user.getId());

            model.addAttribute("cards", cardService.getByHolderId(user.getId()));
            model.addAttribute("wallets", wallets.stream().map(walletMapper::toDto));
            model.addAttribute("walletsTotal", walletService.getTotalBalance(user.getId()));
            model.addAttribute("availableCurrenciesForNewWallets", currencyService.getAllAvailableCurrenciesForUserWallets(user.getId()));
            model.addAttribute("currentWalletCurrencies", wallets);
            model.addAllAttributes(userService.collectActivityAndStats(user.getId()));
            return "dashboard";
        } catch(AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }
}

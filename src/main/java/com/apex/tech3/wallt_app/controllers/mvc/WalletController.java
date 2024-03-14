package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;
    private final WalletMapper walletMapper;
    private final AuthenticationHelper authenticationHelper;

    public WalletController(WalletService walletService, WalletMapper walletMapper, AuthenticationHelper authenticationHelper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("filterOptions", new WalletFilterOptions());
    }

    @GetMapping("/{id}")
    public String getSingleWallet(@PathVariable int id, Model model) {
        model.addAttribute("wallet", walletService.getById(id));
        return "wallet";
    }

    @GetMapping
    public String getAll(@ModelAttribute("filterOptions") WalletFilterOptions filterOptions, Model model) {
        Page<Wallet> wallets = walletService.getAll(filterOptions);
        model.addAttribute("wallets", wallets.getContent());
        model.addAttribute("currentPage", filterOptions.getPage());
        model.addAttribute("totalPages", wallets.getTotalPages());
        return "wallets";
    }

    @PostMapping
    public String addWallet(@ModelAttribute("walletDto") WalletDto walletDto, BindingResult bindingResult,
                            Model model, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "redirect:/dashboard";
        }
        try {
            Wallet wallet = walletMapper.fromDto(walletDto);
            User user = authenticationHelper.tryGetCurrentUser(httpSession);
            walletService.create(wallet, user);
        } catch (Exception e) {
            System.out.println("Error adding card: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}

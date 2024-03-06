package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
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
}

package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("wallets", walletService.getAll());
        return "wallets";
    }

    @GetMapping("/{id}")
    public String getSingleWallet(@PathVariable int id, Model model) {
        model.addAttribute("wallet", walletService.get(id));
        return "wallets";
    }

    @GetMapping("/filter")
    public String getFilterWallets(Model model) {
        model.addAttribute("wallets", walletService.getByHolderAndCurrency(null, null));
        return "wallets";
    }
}

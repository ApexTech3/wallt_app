package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletRestController {

    private final WalletService walletService;

    private final WalletMapper walletMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public WalletRestController(WalletService walletService, WalletMapper walletMapper, AuthenticationHelper authenticationHelper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<WalletDto> getAll() {
        return walletService.getAll().stream().map(walletMapper::toDto).toList();
    }

    @GetMapping("/{userId}")
    public List<WalletDto> getByUserId(@PathVariable int userId) {
        return walletService.getByUserId(userId).stream().map(walletMapper::toDto).toList();
    }


}

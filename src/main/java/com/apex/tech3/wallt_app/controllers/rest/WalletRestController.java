package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.WalletMapper;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletRestController {

    private static final String NOT_ADMIN_ERROR = "You are not authorized to perform this action.";
    private final WalletService walletService;

    private final WalletMapper walletMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public WalletRestController(WalletService walletService, WalletMapper walletMapper, AuthenticationHelper authenticationHelper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping
    public List<WalletDto> getAll(@RequestHeader HttpHeaders headers,
                                  @RequestParam(required = false) Integer holderId,
                                  @RequestParam(required = false) BigDecimal amountGreaterThan,
                                  @RequestParam(required = false) BigDecimal amountLessThan,
                                  @RequestParam(required = false) Integer currencyId) {
        try {
            if (!AuthenticationHelper.isAdmin(authenticationHelper.tryGetUser(headers)))
                throw new AuthorizationException(NOT_ADMIN_ERROR);
            return walletService.getAll( holderId, amountGreaterThan, amountLessThan, currencyId).stream().map(walletMapper::toDto).toList();
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{userId}")
    public List<WalletDto> getByUserId(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            authenticationHelper.tryGetUser(headers);
            return walletService.getByUserId(userId).stream().map(walletMapper::toDto).toList();
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}

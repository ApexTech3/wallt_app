package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TransactionMapper;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.TransactionDto;
import com.apex.tech3.wallt_app.models.dtos.TransactionResponse;
import com.apex.tech3.wallt_app.services.contracts.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TransactionRestController(TransactionService transactionService, TransactionMapper transactionMapper, AuthenticationHelper authenticationHelper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/all")
    public Page<TransactionResponse> getAll(@RequestParam(required = false) Pageable pageable,
                                            @RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) Integer receiverWalletId,
                                            @RequestParam(required = false) Integer senderWalletId,
                                            @RequestParam(required = false) Double amount,
                                            @RequestParam(required = false) String currencySymbol,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) String date) {
        return new PageImpl<>(transactionService.getAll(pageable, id, receiverWalletId, senderWalletId, amount, currencySymbol, status, date == null ? null : Timestamp.valueOf(date))
                                      .stream()
                                      .map(TransactionMapper::toResponse)
                                      .toList());
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{id}")
    public TransactionResponse get(@PathVariable int id) {
        try {
            return TransactionMapper.toResponse(transactionService.get(id));
        } catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping()
    public TransactionResponse create(@RequestHeader @NotNull HttpHeaders headers, @RequestBody TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.fromDto(transactionDto);
        try {
            User user = authenticationHelper.tryGetUser(headers);
            transactionService.create(transaction, user);
            return TransactionMapper.toResponse(transaction);
        } catch(AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch(InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

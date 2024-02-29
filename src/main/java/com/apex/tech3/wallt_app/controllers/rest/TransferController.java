package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TransferMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.models.dtos.TransferResponse;
import com.apex.tech3.wallt_app.services.contracts.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;
    private final TransferMapper transferMapper;
    private final AuthenticationHelper helper;

    @Autowired
    public TransferController(TransferService transferService, TransferMapper transferMapper, AuthenticationHelper helper) {
        this.transferService = transferService;
        this.transferMapper = transferMapper;
        this.helper = helper;

    }

    @PostMapping("/deposits")
    public TransferResponse deposit(@RequestHeader HttpHeaders headers, @Valid @RequestBody TransferDto transferDto) {
        try {
            User user = helper.tryGetUser(headers);
            return transferMapper.toDto(transferService.deposit(transferMapper.fromDto(transferDto), user));
        } catch (InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        //todo implement separate authorization and authentication exceptions
    }

    @PostMapping("/withdrawals")
    public TransferResponse withdraw(@RequestHeader HttpHeaders headers, @Valid @RequestBody TransferDto transferDto) {
        try {
            User user = helper.tryGetUser(headers);
            return transferMapper.toDto(transferService.withdraw(transferMapper.fromDto(transferDto), user));
        } catch (InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        //todo implement separate authorization and authentication exceptions
    }
}

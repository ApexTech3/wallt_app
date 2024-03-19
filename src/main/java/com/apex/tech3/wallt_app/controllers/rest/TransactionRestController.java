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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TransactionRestController(TransactionService transactionService, AuthenticationHelper authenticationHelper, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/all")
    public Page<TransactionResponse> getAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "100") int pageSize,
                                            @RequestParam(required = false, defaultValue = "stampCreated") String sortBy,
                                            @RequestParam(required = false, defaultValue = "DESC") String sortDirection,
                                            @RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) Integer receiverWalletId,
                                            @RequestParam(required = false) Integer senderWalletId,
                                            @RequestParam(required = false) Double amount,
                                            @RequestParam(required = false) Double amountGreaterThan,
                                            @RequestParam(required = false) Double amountLesserThan,
                                            @RequestParam(required = false, defaultValue = "SUCCESSFUL") String status,
                                            @RequestParam(required = false) LocalDate date,
                                            @RequestParam(required = false) LocalDate laterThan,
                                            @RequestParam(required = false) LocalDate earlierThan) {


        try {
            if(pageNumber < 0 || pageSize < 1) {
                throw new IllegalArgumentException("Invalid page number or page size");
            }
            return transactionService.getAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)),
                                             id, receiverWalletId, senderWalletId, amount, amountGreaterThan, amountLesserThan, status, date, laterThan, earlierThan)
                    .map(transactionMapper::toResponse);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable int id) {
        try {
            return transactionMapper.toResponse(transactionService.getById(id));
        } catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/sender/{senderId}")
    public Page<TransactionResponse> getBySender(@PathVariable int senderId,
                                                 @RequestParam(required = false, defaultValue = "SUCCESSFUL") String status,
                                                 @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                 @RequestParam(required = false, defaultValue = "100") int pageSize,
                                                 @RequestParam(required = false, defaultValue = "stampCreated") String sortBy,
                                                 @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        try {
            if(pageNumber < 0 || pageSize < 1) {
                throw new IllegalArgumentException("Invalid page number or page size");
            }
            return transactionService.getBySender(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)),
                                                  senderId, status)
                    .map(transactionMapper::toResponse);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/receiver/{receiverId}")
    public Page<TransactionResponse> getByReceiver(@PathVariable int receiverId,
                                                   @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                   @RequestParam(required = false, defaultValue = "SUCCESSFUL") String status,
                                                   @RequestParam(required = false, defaultValue = "100") int pageSize,
                                                   @RequestParam(required = false, defaultValue = "stampCreated") String sortBy,
                                                   @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        try {
            if(pageNumber < 0 || pageSize < 1) {
                throw new IllegalArgumentException("Invalid page number or page size");
            }
            return transactionService.getByReceiver(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)),
                                                    receiverId, status)
                    .map(transactionMapper::toResponse);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping()
    public TransactionResponse create(@RequestHeader @NotNull HttpHeaders headers, @RequestParam BigDecimal amount,
                                      @RequestParam int senderWalletId, @RequestParam int receiverWalletId) {

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(amount);
        transactionDto.setSenderWalletId(senderWalletId);
        transactionDto.setReceiverWalletId(receiverWalletId);
        Transaction transaction = transactionMapper.fromDto(transactionDto);
        try {
            User user = authenticationHelper.tryGetUser(headers);
            transactionService.create(transaction, user);
            return transactionMapper.toResponse(transaction);
        } catch(AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch(InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

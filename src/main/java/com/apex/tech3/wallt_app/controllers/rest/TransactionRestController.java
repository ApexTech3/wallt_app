package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TransactionMapper;
import com.apex.tech3.wallt_app.models.dtos.TransactionDto;
import com.apex.tech3.wallt_app.services.contracts.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public Page<TransactionDto> getAll(@RequestParam(required = false) Pageable pageable,
                                       @RequestParam(required = false) Integer id,
                                       @RequestParam(required = false) Integer receiverWalletId,
                                       @RequestParam(required = false) Integer senderWalletId,
                                       @RequestParam(required = false) Double amount,
                                       @RequestParam(required = false) String currencySymbol,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String date) {
        return new PageImpl<>(transactionService.getAll(pageable, id, receiverWalletId, senderWalletId, amount, currencySymbol, status, date == null ? null : Timestamp.valueOf(date))
                                      .stream()
                                      .map(TransactionMapper::toDto)
                                      .toList());
    }
}

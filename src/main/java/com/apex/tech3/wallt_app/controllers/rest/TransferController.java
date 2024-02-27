package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.helpers.TransferMapper;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Deposit;
import com.apex.tech3.wallt_app.services.contracts.TransferService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;
    private final TransferMapper transferMapper;
    private final UserService userService;//todo remove when headers validation is available

    @Autowired
    public TransferController(TransferService transferService, TransferMapper transferMapper, UserService userService) {
        this.transferService = transferService;
        this.transferMapper = transferMapper;
        this.userService = userService;
    }

    @PostMapping("/deposits")
    public Transfer deposit(@RequestHeader HttpHeaders headers, @Validated(Deposit.class) @RequestBody TransferDto transferDto) {
//        User user = helper.tryGetUser(headers);
//todo implement headers validation for user when available
        User user = userService.get(1);
        return transferService.deposit(transferMapper.fromDto(transferDto), user);
    }

    @PostMapping("/withdrawals")
    public Transfer withdraw() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

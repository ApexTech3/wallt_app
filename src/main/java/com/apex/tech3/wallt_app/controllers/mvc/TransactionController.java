package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TransactionMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.TransactionDto;
import com.apex.tech3.wallt_app.services.contracts.TransactionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final AuthenticationHelper helper;
    private final TransactionMapper transactionMapper;


    public TransactionController(TransactionService transactionService, AuthenticationHelper helper, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.helper = helper;
        this.transactionMapper = transactionMapper;
    }


    @PostMapping("/new")
    public String transaction(@Valid @ModelAttribute("transactionDto") TransactionDto transactionDto, HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            transactionService.create(transactionMapper.fromWebDto(transactionDto), user);
            return "redirect:/dashboard";
        } catch(AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }
}

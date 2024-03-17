package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.TransferMapper;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.services.contracts.TransferService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;
    private final AuthenticationHelper helper;
    private final TransferMapper transferMapper;

    @Autowired
    public TransferController(TransferService transferService, AuthenticationHelper helper, TransferMapper transferMapper) {
        this.transferService = transferService;
        this.helper = helper;
        this.transferMapper = transferMapper;
    }

    @PostMapping("/deposit")
    public String deposit(@Valid @ModelAttribute("transferDto") TransferDto transferDto, HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            transferService.deposit(transferMapper.fromDto(transferDto), user);
            return "redirect:/dashboard";
        } catch (AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/withdrawal")
    public String initiateWithdrawal(@Valid @ModelAttribute("transferDto") TransferDto transferDto, HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            transferService.initiateWithdraw(transferMapper.fromDto(transferDto), user);
            return "redirect:/dashboard";
        } catch (AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/withdrawal")
    public String showPendingWithdrawals(HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            List<Transfer> userPendingWithdrawals = transferService.getUserPendingWithdrawals(user);
            return "redirect:/dashboard";
        } catch (AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/withdrawal/confirm")
    public String confirmWithdrawal(@RequestParam("withdrawalId") int withdrawalId, HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            transferService.confirmWithdraw(transferService.get(withdrawalId), user);
            return "redirect:/dashboard";
        } catch (AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/withdrawal/cancel")
    public String cancelWithdrawal(@RequestParam("withdrawalId") int withdrawalId, HttpSession httpSession) {
        try {
            User user = helper.tryGetCurrentUser(httpSession);
            transferService.cancelWithdraw(transferService.get(withdrawalId), user);
            return "redirect:/dashboard";
        } catch (AuthorizationException | AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }
}

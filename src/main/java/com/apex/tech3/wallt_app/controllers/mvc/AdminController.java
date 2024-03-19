package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.CardDto;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import com.apex.tech3.wallt_app.models.filters.UserFilterOptions;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public AdminController(UserService userService, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute
    public void populateAttributes(HttpSession httpSession, Model model, HttpServletRequest request) {
        boolean isAuthenticated = httpSession.getAttribute("currentUser") != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAuthenticated ? httpSession.getAttribute("isAdmin") : false);
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("filterOptions", new UserFilterOptions());
        model.addAttribute("cardDto", new CardDto());
        model.addAttribute("walletDto", new WalletDto());
        model.addAttribute("transferDto", new TransferDto());
    }

    @GetMapping("/users")
    public String getAllUsers(@ModelAttribute("filterOptions") UserFilterOptions filterOptions, Model model, HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
            model.addAttribute("currentUser", user);
            model.addAttribute("filterOptions", filterOptions);
            if (!AuthenticationHelper.isAdmin(user)) {
                throw new AuthorizationException("You are not authorized to perform this operation");
            }
            Pageable pageable = createPageable(filterOptions);
            Page<User> userPage = userService.getAll(pageable, filterOptions.getHolderId(),
                    filterOptions.getUsername(), filterOptions.getFirstName(), filterOptions.getMiddleName(),
                    filterOptions.getLastName(), filterOptions.getEmail(), filterOptions.getPhone());
            model.addAttribute("users", userService.getAll(pageable, filterOptions.getHolderId(),
                    filterOptions.getUsername(), filterOptions.getFirstName(), filterOptions.getMiddleName(),
                    filterOptions.getLastName(), filterOptions.getEmail(), filterOptions.getPhone()));
            model.addAttribute("currentPage", userPage.getNumber());
            model.addAttribute("totalPages", userPage.getTotalPages());
            model.addAttribute("showPagination", true);
            return "users";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/transactions")
    public String getAllTransactions(Model model, HttpSession httpSession) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(httpSession);
            model.addAttribute("currentUser", user);
            if (!AuthenticationHelper.isAdmin(user)) {
                throw new AuthorizationException("You are not authorized to perform this operation");
            }
            model.addAttribute("activities", userService.collectAllActivity());
            return "transactions";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "errorView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
    }

    private Pageable createPageable(UserFilterOptions filterOptions) {
        final int PAGE_SIZE = 10;
        if (filterOptions.getSortBy() == null || filterOptions.getSortBy().isEmpty())
            return PageRequest.of(filterOptions.getPage(), PAGE_SIZE);
        Sort sort = Sort.by(filterOptions.getSortBy());
        sort = filterOptions.getSortOrder().equals("desc") ? sort.descending() : sort.ascending();
        return PageRequest.of(filterOptions.getPage(), PAGE_SIZE, sort);
    }

    @GetMapping("/user/{id}/block")
    private String changeBlockedStatus(@PathVariable int id, HttpSession httpSession) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(httpSession);
            userService.switchBlockedStatus(id, user);
        } catch(Exception e) {
            System.out.println("Error changing blocked status: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}

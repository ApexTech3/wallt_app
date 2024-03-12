package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.CardMapper;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.CardDto;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;
    private final AuthenticationHelper authenticationHelper;
    private final CardMapper cardMapper;

    @Autowired
    public CardController(CardService cardService, AuthenticationHelper authenticationHelper, CardMapper cardMapper) {
        this.cardService = cardService;
        this.authenticationHelper = authenticationHelper;
        this.cardMapper = cardMapper;
    }
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cards", cardService.getAll());
        return "cards";
    }

    @GetMapping("/{id}")
    public String getSingleCard(@PathVariable int id, Model model) {
        model.addAttribute("card", cardService.getById(id));
        return "card";
    }

    @PostMapping
    public String addCard(@Valid @ModelAttribute("cardDto") CardDto cardDto, BindingResult bindingResult, Model model,
                          HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "redirect:/";
        }
        try {
            Card card = cardMapper.fromDto(cardDto);
            User user = authenticationHelper.tryGetCurrentUser(httpSession);
            cardService.create(card, user);
        } catch (Exception e) {
            System.out.println("Error adding card: " + e.getMessage());
        }
        return "redirect:/";
    }
}

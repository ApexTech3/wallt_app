package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.services.contracts.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cards", cardService.getAll());
        return "cards";
    }

    @GetMapping("/{id}")
    public String getSingleCard(@PathVariable int id, Model model) {
        model.addAttribute("card", cardService.get(id));
        return "card";
    }
}

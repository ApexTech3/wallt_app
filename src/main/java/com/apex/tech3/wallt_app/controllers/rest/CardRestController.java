package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.CardMapper;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.CardDto;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Register;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {

    private final CardService cardService;

    private final CardMapper cardMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public CardRestController(CardService cardService, CardMapper cardMapper,AuthenticationHelper authenticationHelper) {
        this.cardService = cardService;
        this.cardMapper = cardMapper;
        this.authenticationHelper = authenticationHelper;
    }
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    public List<CardDto> getAll() {
        return cardService.getAll().stream().map(CardMapper::toDto).toList();
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{userId}")
    public List<CardDto> getByUserId(@PathVariable int userId) {
        List<CardDto> cards = cardService.getByHolderId(userId).stream().map(CardMapper::toDto).toList();
        if(cards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cards found for this user.");
        }
        return cards;
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping
    public CardDto createCard(@RequestHeader @NotNull HttpHeaders headers,
                              @Validated(Register.class) @RequestBody CardDto cardDto) {

        try {
            User user = authenticationHelper.tryGetUser(headers);
            Card card = cardMapper.fromDto(cardDto);
            if(cardService.exists(card.getNumber())) {
                throw new EntityDuplicateException("Card with this number already exists.");
            }
            card.setHolder(user);
            cardService.create(card);
            return CardMapper.toDto(card);
        } catch(AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch(EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{id}")
    public void deactivateCard(@RequestHeader @NotNull HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Card card = cardService.get(id);
            if (card.getHolder().getId() != user.getId()) {
                throw new AuthorizationException("You are not the owner of this card.");
            }
            card.setActive(false);
            cardService.update(card);
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}

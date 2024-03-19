package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.CardRepository;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    @Autowired
    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card getById(int id) {
        if (repository.findByIdAndIsActiveTrue(id) == null) {
            throw new EntityNotFoundException("Card", id);
        }
        return repository.findByIdAndIsActiveTrue(id);
    }

    @Override
    public Set<Card> getByHolderId(int userId) {
        return repository.findByHolderId(userId);
    }

    @Override
    public Set<Card> getByHolderIdAndActive(int userId) {
        return repository.findByHolderIdAndIsActiveTrue(userId);
    }

    @Override
    public List<Card> getAll() {
        return repository.findAll();
    }

    @Override
    public Card create(Card card, User user) {
        if (exists(card.getNumber())) {
            throw new EntityDuplicateException("Card", "number", card.getNumber());
        }
        card.setHolder(user);
        card.setActive(true);
        return repository.save(card);
    }

    @Override
    public Card update(Card card, User user) {
        Card existingCard = repository.findById(card.getId()).orElseThrow(() -> new EntityNotFoundException("Card", card.getId()));
        if (existingCard.getHolder().getId() != user.getId()) {
            throw new AuthorizationException("You are not the owner of this card.");
        }
        if (exists(card.getNumber())) {
            throw new EntityDuplicateException("Card with this number already exists.");
        }
        card.setHolder(user);
        return repository.save(card);
    }

    @Override
    public void deactivate(int id, User user) {
        Card card = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Card", id));
        if (card.getHolder().getId() != user.getId()) {
            throw new AuthorizationException("You are not the owner of this card.");
        }
        card.setActive(false);
        repository.save(card);
    }

    @Override
    public void changeStatus(int id, User user) {
        Card card = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Card", id));
        if (card.getHolder().getId() != user.getId()) {
            throw new AuthorizationException("You are not the owner of this card.");
        }
        card.setActive(!card.isActive());
        repository.save(card);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(String number) {
        return repository.existsByNumber(number);
    }
}

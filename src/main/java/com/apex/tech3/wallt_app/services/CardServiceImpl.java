package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.repositories.CardRepository;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    @Autowired
    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Card", id));
    }

    @Override
    public List<Card> getAll() {
        return repository.findAll();
    }

    @Override
    public Card create(Card card) {
        return repository.save(card);
    }
    @Override
    public Card update(Card card) {
        return repository.save(card);
    }
    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}

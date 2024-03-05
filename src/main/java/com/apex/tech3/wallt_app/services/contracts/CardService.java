package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;

import java.util.List;
import java.util.Set;

public interface CardService {
    Card getById(int id);

    Set<Card> getByHolderId(int userId);

    List<Card> getAll();

    Card create(Card card, User user);

    Card update(Card card, User user);

    void delete(int id);

    boolean exists(String number);

    void deactivate(int id, User user);
}

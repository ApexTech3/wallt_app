package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Card;

import java.util.List;
import java.util.Set;

public interface CardService {
    Card get(int id);

    Set<Card> getByHolderId(int userId);

    List<Card> getAll();

    Card create(Card card);

    Card update(Card card);

    void delete(int id);

    boolean exists(String number);

}

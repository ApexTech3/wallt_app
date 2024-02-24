package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.repositories.contracts.CardRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepositoryImpl extends AbstractCRUDRepository<Card> implements CardRepository {
    public CardRepositoryImpl(SessionFactory sessionFactory) {
        super(Card.class, sessionFactory);
    }
}

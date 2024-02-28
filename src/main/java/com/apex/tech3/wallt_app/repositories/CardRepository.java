package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import jakarta.persistence.SecondaryTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByNumber(String number);
    Card findByNumber(String number);

    Set<Card> findByHolderId(int holderId);
}

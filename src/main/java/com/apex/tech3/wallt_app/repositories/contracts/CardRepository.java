package com.apex.tech3.wallt_app.repositories.contracts;

import com.apex.tech3.wallt_app.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}

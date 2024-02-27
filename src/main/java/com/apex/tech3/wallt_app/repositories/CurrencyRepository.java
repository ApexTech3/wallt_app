package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}

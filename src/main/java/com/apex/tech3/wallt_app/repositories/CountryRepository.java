package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country getByName(String name);
}

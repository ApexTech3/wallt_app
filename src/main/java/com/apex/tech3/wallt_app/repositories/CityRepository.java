package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    City getByName(String name);

}

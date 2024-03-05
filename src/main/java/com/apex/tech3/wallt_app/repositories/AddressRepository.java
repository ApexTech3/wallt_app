package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Address;
import com.apex.tech3.wallt_app.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}

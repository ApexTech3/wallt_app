package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}

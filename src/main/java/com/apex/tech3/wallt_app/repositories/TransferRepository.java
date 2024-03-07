package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransferRepository extends JpaRepository<Transfer, Integer>, JpaSpecificationExecutor<Transfer> {
}

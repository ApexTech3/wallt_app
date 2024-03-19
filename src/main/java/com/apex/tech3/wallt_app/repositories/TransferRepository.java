package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Integer>, JpaSpecificationExecutor<Transfer> {
    List<Transfer> findAllByWalletHolderAndStatus(User holder, StatusEnum status);
}

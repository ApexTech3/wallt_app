package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;

import java.util.List;

public interface TransferService {
    Transfer deposit(Transfer transfer, User user);
    Transfer withdraw(Transfer transfer, User user);

    List<Transfer> getUserTransfers(User user);

    List<Transfer> getUserTransfers(int userId);
}

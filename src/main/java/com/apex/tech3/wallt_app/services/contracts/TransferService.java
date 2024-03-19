package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;

import java.util.List;

public interface TransferService {
    Transfer deposit(Transfer transfer, User user);

    Transfer initiateWithdraw(Transfer transfer, User user);

    Transfer editWithdraw(Transfer transfer, User user);

    Transfer confirmWithdraw(Transfer transfer, User user);

    Transfer cancelWithdraw(Transfer transfer, User user);

    List<Transfer> getUserPendingWithdrawals(User user);

    List<Transfer> getUserTransfers(User user);

    List<Transfer> getUserTransfers(int userId);

    List<Transfer> getAllTransfers();

    Transfer get(int id);
}

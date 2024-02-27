package com.apex.tech3.wallt_app.services.contracts;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;

public interface TransferService {
    Transfer deposit(Transfer transfer, User user);
}

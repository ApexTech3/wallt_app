package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.clients.DummyCardClient;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.CardDetails;
import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import com.apex.tech3.wallt_app.repositories.TransferRepository;
import com.apex.tech3.wallt_app.services.contracts.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    private static final String MODIFY_TRANSFER_ERROR_MESSAGE = "Only admin or wallet owner can transfer funds.";
    private final TransferRepository repository;
    private final DummyCardClient dummyCardClient;

    @Autowired
    public TransferServiceImpl(TransferRepository repository, DummyCardClient dummyCardClient) {
        this.repository = repository;
        this.dummyCardClient = dummyCardClient;
    }

    @Override
    public Transfer deposit(Transfer transfer, User user) {
        checkModifyPermissions(transfer, user);
        transfer.setDirection(DirectionEnum.DEPOSIT);
        CardDetails cardDetails= new CardDetails(transfer.getCard().getNumber(), transfer.getCard().getCardHolderName(),
                transfer.getCard().getExpirationMonth(), transfer.getCard().getExpirationYear(),
                transfer.getCard().getCvv(), transfer.getAmount());
        transfer.setStatus(dummyCardClient.tryPay(cardDetails)
                ? StatusEnum.SUCCESSFUL : StatusEnum.FAILED);
        if (transfer.getStatus().equals(StatusEnum.SUCCESSFUL))
            transfer.getWallet().setAmount(transfer.getWallet().getAmount().add(transfer.getAmount()));
        return repository.save(transfer);
    }


    private void checkModifyPermissions(Transfer transfer, User user) {
        //todo when user admin is done  !user.isAdmin() &&
        if (!transfer.getWallet().getHolder().equals(user)) {
            throw new AuthorizationException(MODIFY_TRANSFER_ERROR_MESSAGE);
        }
    }
}

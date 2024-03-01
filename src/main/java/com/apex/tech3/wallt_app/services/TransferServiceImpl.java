package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.clients.DummyCardClient;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.helpers.FundsHelper;
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
    private static final String TRANSFER_ERROR_MESSAGE = "Only admin or wallet owner can transfer funds.";
    private static final String CARD_ERROR_MESSAGE = "You can transfer funds only from your own card.";
    private final TransferRepository repository;
    private final DummyCardClient dummyCardClient;

    @Autowired
    public TransferServiceImpl(TransferRepository repository, DummyCardClient dummyCardClient) {
        this.repository = repository;
        this.dummyCardClient = dummyCardClient;
    }

    @Override
    public Transfer deposit(Transfer transfer, User user) {
        transfer.setDirection(DirectionEnum.DEPOSIT);
        buildDepositOrWithdrawal(transfer, user);
        return repository.save(transfer);
    }

    @Override
    public Transfer withdraw(Transfer transfer, User user) {
        FundsHelper.validateWalletHasEnoughFunds(transfer.getAmount(),transfer.getWallet().getAmount());
        transfer.setDirection(DirectionEnum.WITHDRAW);
        buildDepositOrWithdrawal(transfer, user);
        return repository.save(transfer);
    }

    private void buildDepositOrWithdrawal(Transfer transfer, User user) {
        checkModifyPermissions(transfer, user);
        verifyCardOwner(transfer, user);
        CardDetails cardDetails = buildCardDetails(transfer);
        transfer.setStatus(dummyCardClient.tryPay(cardDetails)
                ? StatusEnum.SUCCESSFUL : StatusEnum.FAILED);
        transfer.setCurrency(transfer.getWallet().getCurrency());
        if (transfer.getStatus().equals(StatusEnum.SUCCESSFUL))
            transfer.getWallet().setAmount(transfer.getDirection().equals(DirectionEnum.DEPOSIT) ?
                    transfer.getWallet().getAmount().add(transfer.getAmount()) :
                    transfer.getWallet().getAmount().subtract(transfer.getAmount()));
    }

    private CardDetails buildCardDetails(Transfer transfer) {
        return new CardDetails(transfer.getCard().getNumber(), transfer.getCard().getCardHolderName(),
                transfer.getCard().getExpirationMonth(), transfer.getCard().getExpirationYear(),
                transfer.getCard().getCvv(), transfer.getAmount());
    }

    private void checkModifyPermissions(Transfer transfer, User user) {
        //todo when user admin is done  !user.isAdmin() &&
        if (!transfer.getWallet().getHolder().equals(user)) {
            throw new AuthorizationException(TRANSFER_ERROR_MESSAGE);
        }
    }

    private void verifyCardOwner(Transfer transfer, User user) {
        if (!transfer.getCard().getHolder().equals(user)) {
            throw new AuthorizationException(CARD_ERROR_MESSAGE);
        }
    }
}

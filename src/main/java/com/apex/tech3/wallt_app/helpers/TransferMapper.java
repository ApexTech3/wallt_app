package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.FinancialActivity;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.models.dtos.TransferResponse;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {
    private final CardService cardService;
    private final WalletService walletService;

    @Autowired
    public TransferMapper(CardService cardService, WalletService walletService) {
        this.cardService = cardService;
        this.walletService = walletService;
    }

    public Transfer fromDto(TransferDto transferDto) {
        Transfer transfer = new Transfer();
        transfer.setAmount(transferDto.getAmount());
        transfer.setCard(cardService.getById(transferDto.getCardId()));
        transfer.setWallet(walletService.getById(transferDto.getWalletId()));
        return transfer;
    }

    public TransferResponse toDto(Transfer transfer) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setCardNumber(transfer.getCard().getNumber());
        transferResponse.setAmount(transfer.getAmount());
        transferResponse.setStatus(transfer.getStatus());
        return transferResponse;
    }

    public FinancialActivity depositToActivity(Transfer transfer) {
        FinancialActivity activity = new FinancialActivity();
        String cardNumber = transfer.getCard().getNumber();
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        activity.setDescription("Deposited through ****" + lastFour);
        activity.setAmount(transfer.getAmount());
        activity.setCurrencySymbol(transfer.getWallet().getCurrency().getSymbol());
        activity.setTimestamp(transfer.getStampCreated());
        activity.setWalletId(transfer.getWallet().getId());
        activity.setStatus(transfer.getStatus());
        activity.setType("DEPOSIT");
        return activity;
    }

    public FinancialActivity withdrawalToActivity(Transfer transfer) {
        FinancialActivity activity = new FinancialActivity();
        String cardNumber = transfer.getCard().getNumber();
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        activity.setDescription("Withdrawn through ****" + lastFour);
        activity.setAmount(transfer.getAmount().negate());
        activity.setCurrencySymbol(transfer.getWallet().getCurrency().getSymbol());
        activity.setTimestamp(transfer.getStampCreated());
        activity.setWalletId(transfer.getWallet().getId());
        activity.setStatus(transfer.getStatus());
        activity.setType("WITHDRAW");
        return activity;
    }
}

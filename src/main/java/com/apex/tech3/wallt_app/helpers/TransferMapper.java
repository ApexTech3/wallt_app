package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.dtos.TransferDto;
import com.apex.tech3.wallt_app.services.contracts.CardService;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {
    private final CardService cardService;
    private final WalletService walletService;
    private final CurrencyService currencyService;

    @Autowired
    public TransferMapper(CardService cardService, WalletService walletService, CurrencyService currencyService) {
        this.cardService = cardService;
        this.walletService = walletService;
        this.currencyService = currencyService;
    }

    public Transfer fromDto(TransferDto transferDto) {
        Transfer transfer = new Transfer();
        transfer.setAmount(transferDto.getAmount());
        transfer.setCard(cardService.get(transferDto.getCardId()));
        transfer.setCurrency(currencyService.get(transferDto.getCurrencyId()));
        transfer.setWallet(walletService.get(transferDto.getWalletId()));
        return transfer;
    }
}

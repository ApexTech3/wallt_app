package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.dtos.WalletDto;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public Wallet fromDto(WalletDto walletDto) {
        Wallet newWallet = new Wallet();
        newWallet.setCurrency(walletDto.getCurrency());
        return newWallet;
    }

    public WalletDto toDto(Wallet wallet) {
        WalletDto newWalletDto = new WalletDto();
        newWalletDto.setCurrency(wallet.getCurrency());
        newWalletDto.setAmount(wallet.getAmount());
        return newWalletDto;
    }
}

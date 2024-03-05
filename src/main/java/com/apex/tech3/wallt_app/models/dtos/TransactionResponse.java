package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Wallet;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class TransactionResponse {

    private BigDecimal amount;

    private Currency currency;

    private Wallet receiverWallet;

    private Wallet senderWallet;

    private String status;

    private String timestamp;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return Map.of("currency", currency.getSymbol(),
                      "receiverWallet", receiverWallet.getHolder().getUsername(),
                      "senderWallet", senderWallet.getHolder().getUsername());
    }
}
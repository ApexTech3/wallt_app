package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Wallet;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class TransactionResponse {

    private BigDecimal amountSent;

    private Currency senderCurrency;

    private double exchangeRate;

    private BigDecimal amountReceived;

    private Currency receiverCurrency;

    private Wallet senderWallet;

    private Wallet receiverWallet;

    private String status;

    private String timestamp;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return Map.of("senderCurrency", senderWallet.getCurrency().getTicker(),
                      "receiverCurrency", receiverWallet.getCurrency().getTicker(),
                      "receiverWallet", receiverWallet.getHolder().getUsername(),
                      "senderWallet", senderWallet.getHolder().getUsername());
    }
}

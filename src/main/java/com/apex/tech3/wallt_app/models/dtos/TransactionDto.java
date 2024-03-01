package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Data
public class TransactionDto {

    private BigDecimal amount;

    private Currency currency;

    private Wallet receiverWallet;

    private Wallet senderWallet;

    private StatusEnum status;

    private Timestamp timestamp;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return Map.of("currency", currency.getSymbol(),
                      "receiverWallet", receiverWallet.getHolder().getUsername(),
                      "senderWallet", senderWallet.getHolder().getUsername());
    }
}

package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Currency;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class WalletDto {

    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return Map.of("amount", amount.toString(), "currency", currency.getSymbol());
    }

}

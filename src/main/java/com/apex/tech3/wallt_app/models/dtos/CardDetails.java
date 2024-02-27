package com.apex.tech3.wallt_app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CardDetails {
    String number;
    String cardHolderName;
    String expirationMonth;
    String expirationYear;
    String cvv;
    BigDecimal amount;
}

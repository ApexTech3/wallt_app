package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferResponse {
    private String cardNumber;
    private BigDecimal amount;
    private StatusEnum status;

}

package com.apex.tech3.wallt_app.models.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {

    private BigDecimal amount;

    private String currency;

    private int senderWalletId;

    private int receiverWalletId;



}

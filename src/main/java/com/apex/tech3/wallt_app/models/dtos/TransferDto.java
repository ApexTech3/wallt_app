package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.dtos.interfaces.Deposit;
import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransferDto {
    private int id;
    @Positive(message = "Card ID should be positive", groups = {Deposit.class})
    private int cardId;
    @Positive(message = "Wallet ID should be positive", groups = {Deposit.class})
    private int walletId;
    @Positive(message = "Currency ID should be positive", groups = {Deposit.class})
    private int currencyId;
    @Positive(message = "Amount should be positive", groups = {Deposit.class})
    private BigDecimal amount;
    private boolean status;
    private DirectionEnum direction;
    private Timestamp stampCreated;
}

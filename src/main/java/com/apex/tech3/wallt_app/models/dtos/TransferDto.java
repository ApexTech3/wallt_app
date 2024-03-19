package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.dtos.interfaces.Deposit;
import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransferDto {
    @Positive(message = "Card ID should be positive")
    private int cardId;
    @Positive(message = "Wallet ID should be positive")
    private int walletId;
    @NumberFormat(pattern = "#.##")
    @Positive(message = "Amount should be positive")
    private BigDecimal amount;
}

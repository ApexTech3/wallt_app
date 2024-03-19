package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FinancialActivity {

    private String description;

    private BigDecimal amount;

    private String currencySymbol;

    private int walletId;

    private String type;

    private StatusEnum status;

    private Timestamp timestamp;
}

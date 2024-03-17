package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
public class AdminFinancialActivity {
    private String sender;
    private String receiver;
    private BigDecimal amount;

    private String currencySymbol;

    private String type;

    private StatusEnum status;

    private Timestamp timestamp;
}

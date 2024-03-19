package com.apex.tech3.wallt_app.models.filters;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletFilterOptions {
    private Integer holderId;
    private BigDecimal amountGreaterThan;
    private BigDecimal amountLessThan;
    private Integer currencyId;
    private int page;
    private String sortBy;
    private String sortOrder;
}

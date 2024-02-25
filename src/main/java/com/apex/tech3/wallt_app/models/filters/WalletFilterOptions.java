package com.apex.tech3.wallt_app.models.filters;

import lombok.Data;

@Data
public class WalletFilterOptions {
    private Integer holderId;
    private Long amountGreaterThan;
    private Long amountLessThan;
    private Integer currencyId;
}

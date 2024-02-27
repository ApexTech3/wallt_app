package com.apex.tech3.wallt_app.helpers;

import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AmountMapper {
    public static Long amountToCents(Long amount) {
        return Optional.ofNullable(amount).map(a -> a * 100).orElse(null);
    }
}

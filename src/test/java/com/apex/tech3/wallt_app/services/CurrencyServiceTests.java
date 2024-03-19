package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.CurrencyRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTests {
    @Mock
    CurrencyRepository mockRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @InjectMocks
    WalletServiceImpl walletService;



    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        Currency mockCurrency = Helpers.createMockCurrency();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCurrency));
        currencyService.getById(1);

        Mockito.verify(mockRepository, times(1)).findById(1);
    }
}

package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.CurrencyRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTests {
    @Mock
    CurrencyRepository mockRepository;
    @Mock
    WalletService mockWalletService;
    @InjectMocks
    CurrencyServiceImpl service;


    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        Currency currency = Helpers.createMockCurrency();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(currency));

        service.getById(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void getById_Should_Throw_When_CurrencyDoesNotExist() {
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void getByTicker_Should_CallRepository_When_MethodCalled() {
        Currency currency = Helpers.createMockCurrency();
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(currency);

        service.getByTicker("USD");

        Mockito.verify(mockRepository, Mockito.times(1)).findByTicker("USD");
    }

    @Test
    public void getByTicker_Should_ReturnNull_When_CurrencyDoesNotExist() {
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(null);

        Assertions.assertNull(service.getByTicker("USD"));
    }

    @Test
    public void getRateToUsd_Should_CallRepository_When_MethodCalled() {
        Currency currency = Helpers.createMockCurrency();
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(currency);

        service.getRateToUsd("USD");

        Mockito.verify(mockRepository, Mockito.times(1)).findByTicker("USD");
    }

    @Test
    public void getRateToUsd_Should_Return_CorrectRate() {
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(Helpers.createMockCurrency());

        Assertions.assertEquals(1, service.getRateToUsd("USD"));
    }

    @Test
    public void getRate_Should_CallRepository_When_MethodCalled() {
        Currency currency = Helpers.createMockCurrency();
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(currency);
        Mockito.when(mockRepository.findByTicker("EUR")).thenReturn(currency);

        service.getRate("USD", "EUR");

        Mockito.verify(mockRepository, Mockito.times(2)).findByTicker(Mockito.anyString());
    }

    @Test
    public void getRate_Should_Return_CorrectRate() {
        Currency currency = Helpers.createMockCurrency();
        Currency currency2 = Helpers.createMockCurrency();
        currency2.setRateToUsd(2);
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(currency);
        Mockito.when(mockRepository.findByTicker("EUR")).thenReturn(currency2);

        Assertions.assertEquals(0.5, service.getRate("USD", "EUR"));
    }

    @Test
    public void getRate_Should_Return_CorrectRate_When_CurrenciesArePassed() {
        Currency currency = Helpers.createMockCurrency();
        Currency currency2 = Helpers.createMockCurrency();
        currency2.setTicker("EUR");
        currency2.setRateToUsd(4);
        Mockito.when(mockRepository.findByTicker("USD")).thenReturn(currency);
        Mockito.when(mockRepository.findByTicker("EUR")).thenReturn(currency2);

        Assertions.assertEquals(0.25, service.getRate(currency, currency2));
    }

    @Test
    public void getAllAvailableCurrenciesForUserWallets_Should_CallWalletService_When_MethodCalled() {
        User user = Helpers.createMockUser();
        Mockito.when(mockWalletService.getByUserId(1)).thenReturn(List.of(Helpers.createMockWallet()));

        service.getAllAvailableCurrenciesForUserWallets(1);

        Mockito.verify(mockWalletService, Mockito.times(1)).getByUserId(1);
    }
}

package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.repositories.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTests {
    @Mock
    WalletRepository mockRepository;
    @InjectMocks
    WalletServiceImpl service;

    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(mockWallet);

        service.getById(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByIdAndIsActiveTrue(1);
    }

    @Test
    public void getById_Should_Throw_When_WalletDoesNotExist() {
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void getAll_Should_CallRepository_When_MethodCalled() {
        WalletFilterOptions filterOptions = new WalletFilterOptions();

        Mockito.when(mockRepository.findAll(Mockito.<Specification<Wallet>>any(),
                Mockito.eq(PageRequest.of(filterOptions.getPage(), 5)))).thenReturn(new PageImpl<>(List.of(Helpers.createMockWallet())));

        service.getAll(1, BigDecimal.valueOf(1), BigDecimal.valueOf(5), 1);

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.<Specification<Wallet>>any(),
                Mockito.eq(PageRequest.of(filterOptions.getPage(), 5)));
    }

    @Test
    public void getAllUsingWalletFilterOptions_Should_CallRepository_When_MethodCalled() {
        WalletFilterOptions filterOptions = new WalletFilterOptions();

        Mockito.when(mockRepository.findAll(Mockito.<Specification<Wallet>>any(),
                Mockito.eq(PageRequest.of(filterOptions.getPage(), 5)))).thenReturn(new PageImpl<>(List.of(Helpers.createMockWallet())));

        service.getAll(new WalletFilterOptions());

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.<Specification<Wallet>>any(),
                Mockito.eq(PageRequest.of(filterOptions.getPage(), 5)));
    }


    @Test
    public void getByUserId_Should_CallRepository_When_MethodCalled() {
        Wallet wallet = Helpers.createMockWallet();

        service.getByUserId(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderId(1);
    }

    @Test
    public void getActiveByUserId_Should_CallRepository_When_MethodCalled() {
        Wallet wallet = Helpers.createMockWallet();

        service.getActiveByUserId(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderIdAndIsActiveTrue(1);
    }

    @Test
    public void create_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();

        service.create(mockWallet, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockWallet);
    }

    @Test
    public void update_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();

        service.update(mockWallet);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockWallet);
    }

    @Test
    public void changeStatus_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockWallet));
        service.changeStatus(1, user);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockWallet);
    }

    @Test
    public void changeStatus_Should_Not_Save_When_UserNotOwner() {
        Wallet mockWallet = Helpers.createMockWallet();
        User user2 = Helpers.createMockUser();
        user2.setId(2);
        User user = Helpers.createMockUser();
        mockWallet.setHolder(user2);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockWallet));

        Assertions.assertThrows(AuthorizationException.class, () -> service.changeStatus(1, user));

        Mockito.verify(mockRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void changeDefaultWallet_Should_CallRepository_When_MethodCalledAndWalletNotDefault() {
        Wallet mockWallet = Helpers.createMockWallet();
        Wallet mockWallet2 = Helpers.createMockWallet();
        mockWallet.setDefault(false);
        User user = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockWallet));
        Mockito.when(mockRepository.findByHolderIdAndIsDefaultTrue(1)).thenReturn(mockWallet2);
        service.changeDefaultWallet(1, user);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockWallet);

    }

    @Test
    public void changeDefaultWallet_Should_Not_Save_When_UserNotOwner() {
        Wallet mockWallet = Helpers.createMockWallet();
        User user2 = Helpers.createMockUser();
        user2.setId(2);
        User user = Helpers.createMockUser();
        mockWallet.setHolder(user2);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockWallet));

        Assertions.assertThrows(AuthorizationException.class, () -> service.changeDefaultWallet(1, user));

        Mockito.verify(mockRepository, Mockito.never()).save(Mockito.any());
    }


    @Test
    public void delete_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockWallet));
        service.delete(1);

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockWallet);
    }

    @Test
    public void delete_Should_Throw_When_WalletNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.delete(1));
    }

    @Test
    public void checkIfFundsAreAvailable_Should_CallRepository_When_MethodCalled() {
        Wallet wallet = Helpers.createMockWallet();
        BigDecimal amount = new BigDecimal("10000.00");
        Assertions.assertThrows(InsufficientFundsException.class, () -> WalletServiceImpl.checkIfFundsAreAvailable(wallet, amount));

    }

    @Test
    public void testCreditAmount_SufficientFunds() {
        Wallet mockWallet = Helpers.createMockWallet();
        BigDecimal amountToCredit = new BigDecimal("50.00");
        service.creditAmount(mockWallet, amountToCredit);

        Assertions.assertEquals(new BigDecimal("950.00"), mockWallet.getAmount(), "Wallet amount should be reduced by the credited amount");
    }

    @Test
    public void testCreditAmount_InsufficientFunds() {
        Wallet mockWallet = Helpers.createMockWallet();
        mockWallet.setAmount(new BigDecimal("100.00"));
        BigDecimal amountToCredit = new BigDecimal("200.00");

        Assertions.assertThrows(InsufficientFundsException.class, () -> service.creditAmount(mockWallet, amountToCredit));

        Assertions.assertEquals(new BigDecimal("100.00"), mockWallet.getAmount(), "Wallet amount should remain unchanged");
    }

    @Test
    public void testDebitAmount() {
        Wallet mockWallet = Helpers.createMockWallet();
        BigDecimal amountToDebit = new BigDecimal("50.00");
        service.debitAmount(mockWallet, amountToDebit);

        Assertions.assertEquals(new BigDecimal("1050.00"), mockWallet.getAmount(), "Wallet amount should be increased by the debited amount");
    }

    @Test
    public void checkOwnership_Should_Throw_WhenUserNotOwner() {
        Wallet mockWallet = Helpers.createMockWallet();
        User mockUser = Helpers.createMockUser();
        User mockUser2 = Helpers.createMockUser();
        mockUser2.setId(2);
        mockWallet.setHolder(mockUser2);
        Assertions.assertThrows(AuthorizationException.class, () -> service.checkOwnership(mockWallet, mockUser));
    }

    @Test
    public void getDefaultWalletByHolderId_Should_CallRepository_When_MethodCalled() {
        service.getDefaultWalletByHolderId(1);
        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderIdAndIsDefaultTrue(1);
    }

    @Test
    public void getSameOrDefaultWalletByHolderId_Should_CallRepository_When_MethodCalled() {
        Currency mockCurrency = Helpers.createMockCurrency();
        Wallet mockWallet = Helpers.createMockWallet();
        mockWallet.setCurrency(mockCurrency);
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(mockWallet);
        Mockito.when(service.getById(1)).thenReturn(mockWallet);
        Mockito.when(mockRepository.findByHolderIdAndIsDefaultTrue(ArgumentMatchers.anyInt())).thenReturn(mockWallet);
        service.getSameOrDefaultWalletByHolderId(1, 1);
        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderIdAndIsDefaultTrue(ArgumentMatchers.anyInt());
    }

    @Test
    public void getSameOrDefaultWalletByHolderId_Should_Return_WalletByCurrencyId_When_Found() {
        Currency mockCurrency = Helpers.createMockCurrency();
        Wallet mockWallet = Helpers.createMockWallet();
        mockWallet.setCurrency(mockCurrency);
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(mockWallet);
        Mockito.when(service.getById(1)).thenReturn(mockWallet);
        Mockito.when(mockRepository.findByHolderIdAndCurrencyId(1, 1)).thenReturn(mockWallet);

        service.getSameOrDefaultWalletByHolderId(1, 1);
        Mockito.verify(mockRepository, Mockito.times(2)).findByHolderIdAndCurrencyId(1, 1);

    }

    @Test
    public void getTotalBalance_Should_CallRepository_When_MethodCalled() {
        Wallet mockWallet = Helpers.createMockWallet();
        BigDecimal expectedTotal = new BigDecimal("100.00");
        Mockito.when(mockRepository.getTotalBalance(1)).thenReturn(expectedTotal);
        service.getTotalBalance(1);
        Mockito.verify(mockRepository, Mockito.times(1)).getTotalBalance(1);
    }

    @Test
    public void testGetTotalBalance_ReturnsCorrectTotal() {
        BigDecimal expectedTotal = new BigDecimal("100.00");
        Mockito.when(mockRepository.getTotalBalance(1)).thenReturn(expectedTotal);

        BigDecimal actualTotal = service.getTotalBalance(1);

        Mockito.verify(mockRepository, Mockito.times(1)).getTotalBalance(1);

        Assertions.assertEquals(expectedTotal.setScale(2, BigDecimal.ROUND_HALF_UP), actualTotal);
    }


}

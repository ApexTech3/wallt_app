package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.repositories.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        service.getByUserId(1);
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
}

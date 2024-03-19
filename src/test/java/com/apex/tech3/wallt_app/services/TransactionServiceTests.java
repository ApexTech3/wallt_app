package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InsufficientFundsException;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.repositories.TransactionRepository;
import com.apex.tech3.wallt_app.services.contracts.CurrencyService;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    TransactionRepository mockRepository;

    @Mock
    WalletService mockWalletService;

    @Mock
    CurrencyService mockCurrencyService;

    @InjectMocks
    TransactionServiceImpl service;

    @Test
    public void getByUserId_Should_CallRepository_When_MethodCalled() {

        service.getByUserId(1);


        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
    }

    @Test
    public void getByUserId_Should_Return_Empty_When_UserIdDoesNotExist() {
        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class))).thenReturn(null);

        service.getByUserId(1);

        Assertions.assertEquals(0, mockRepository.findAll(Mockito.any(Specification.class)).size());
    }

    @Test
    public void getBySenderId_Should_CallRepository_When_MethodCalled() {

        service.getBySenderId(1);


        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
    }

    @Test
    public void getBySenderId_Should_Return_Empty_When_UserIdDoesNotExist() {
        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class))).thenReturn(null);

        service.getBySenderId(1);

        Assertions.assertEquals(0, mockRepository.findAll(Mockito.any(Specification.class)).size());
    }

    @Test
    public void getByReceiverId_Should_CallRepository_When_MethodCalled() {

        service.getByReceiverId(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));

    }

    @Test
    public void getByReceiverId_Should_Return_Empty_When_UserIdDoesNotExist() {
        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class))).thenReturn(null);

        service.getByReceiverId(1);

        Assertions.assertEquals(0, mockRepository.findAll(Mockito.any(Specification.class)).size());
    }

    @Test
    public void getById_Should_Call_Repository_When_MethodCalled() {
        Transaction transaction = new Transaction();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(transaction));

        service.getById(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void getById_Should_Throw_When_TransactionDoesNotExist() {
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void getBySender_Should_Call_Repository_When_MethodCalled() {
        Transaction mockTransaction = Helpers.createMockTransaction();
        List<Transaction> content = new ArrayList<>();
        content.add(mockTransaction);
        Page<Transaction> mockPage = new PageImpl<>(content);

        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = PageRequest.of(0, 10);
service.getBySender(pageable, 1, "test");
        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    public void getByReceiver_Should_Call_Repository_When_MethodCalled() {
        Transaction mockTransaction = Helpers.createMockTransaction();
        List<Transaction> content = new ArrayList<>();
        content.add(mockTransaction);
        Page<Transaction> mockPage = new PageImpl<>(content);

        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = PageRequest.of(0, 10);
        service.getByReceiver(pageable, 1, "test");

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    public void getSentAmountByUserId_Should_CallRepository_When_MethodCalled() {
        service.getSentAmountByUserId(1);
        Mockito.verify(mockRepository, Mockito.times(1)).getSentAmountByUserId(1);
    }

    @Test
    public void getReceivedAmountByUserId_Should_CallRepository_When_MethodCalled() {
        service.getReceivedAmountByUserId(1);
        Mockito.verify(mockRepository, Mockito.times(1)).getReceivedAmountByUserId(1);
    }

    @Test
    public void getAll_Should_Call_Repository_When_MethodCalled() {
        Transaction mockTransaction = Helpers.createMockTransaction();
        List<Transaction> content = new ArrayList<>();
        content.add(mockTransaction);
        Page<Transaction> mockPage = new PageImpl<>(content);
        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = PageRequest.of(0, 10);
        service.getAll(pageable, 1, 2, 3, 100.0, 50.0, 200.0, "test", LocalDate.now(), LocalDate.now(), LocalDate.now());

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    public void getAll_Should_Create_Default_Pageable_When_Pageable_Is_Null() {
        Transaction mockTransaction = Helpers.createMockTransaction();
        List<Transaction> content = new ArrayList<>();
        content.add(mockTransaction);
        Page<Transaction> mockPage = new PageImpl<>(content);
        Mockito.when(mockRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(mockPage);

        service.getAll(null, 1, 2, 3, 100.0, 50.0, 200.0, "test", LocalDate.now(), LocalDate.now(), LocalDate.now());

        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.eq(PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "stampCreated"))));
    }

    @Test
    public void getAll_Should_CallRepository_When_MethodCalled() {
        service.getAll();
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }


    @Test
    public void create_Should_CallRepository_When_MethodCalled() {
        Transaction transaction = Helpers.createMockTransaction();
        User user = Helpers.createMockUser();
        service.create(transaction, user);
        Mockito.verify(mockRepository, Mockito.times(1)).save(transaction);
    }

    @Test
    public void create_Should_ThrowAuthorizationException_When_UserNotHolder() {
        User user = Helpers.createMockUser();
        User user2 = Helpers.createMockUser();
        Wallet senderWallet2 = Helpers.createMockWallet();
        senderWallet2.setHolder(user2);
        Transaction transaction = Helpers.createMockTransaction();
        transaction.setSenderWallet(senderWallet2);
        Mockito.doThrow(new AuthorizationException("User is not the holder of the sender wallet")).when(mockWalletService).checkOwnership(senderWallet2, user);
        Assertions.assertThrows(AuthorizationException.class, () -> service.create(transaction, user));
    }

    @Test
    public void create_Should_ThrowInsufficientFundsException_When_DebitFails() {
        User user = Helpers.createMockUser();
        Wallet senderWallet = Helpers.createMockWallet();
        Wallet receiverWallet = Helpers.createMockWallet();
        Transaction transaction = Helpers.createMockTransaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        BigDecimal amountInSenderCurrency = BigDecimal.valueOf(1000);
        transaction.setAmount(amountInSenderCurrency);
        Mockito.when(mockCurrencyService.getRate(senderWallet.getCurrency(), receiverWallet.getCurrency())).thenReturn(2.0);

        BigDecimal exchangeRate = BigDecimal.valueOf(2.0);
        BigDecimal amountInReceiverCurrency = amountInSenderCurrency.multiply(exchangeRate);

        Mockito.doThrow(new InsufficientFundsException("Insufficient funds")).when(mockWalletService).debitAmount(receiverWallet, amountInReceiverCurrency);

        InsufficientFundsException exception = Assertions.assertThrows(InsufficientFundsException.class, () -> service.create(transaction, user));
        Assertions.assertEquals("Insufficient funds", exception.getMessage());
    }


}

package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Transaction;
import com.apex.tech3.wallt_app.models.filters.TransactionSpecification;
import com.apex.tech3.wallt_app.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import java.awt.print.Pageable;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    TransactionRepository mockRepository;

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

       Assertions.assertEquals(0,mockRepository.findAll(Mockito.any(Specification.class)).size());
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

        Assertions.assertEquals(0,mockRepository.findAll(Mockito.any(Specification.class)).size());
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

        Assertions.assertEquals(0,mockRepository.findAll(Mockito.any(Specification.class)).size());
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






}

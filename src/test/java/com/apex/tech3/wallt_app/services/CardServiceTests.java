package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CardServiceTests {
    @Mock
    CardRepository mockRepository;
    @InjectMocks
    CardServiceImpl service;

    @Test
    public void getAll_Should_CallRepository_When_MethodCalled() {
        service.getAll();

        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }


    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(mockCard);

        service.getById(1);

        Mockito.verify(mockRepository, Mockito.times(2)).findByIdAndIsActiveTrue(1);
    }

    @Test
    public void getById_Should_Throw_When_CardDoesNotExist() {
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void getByHolderId_Should_CallRepository_When_MethodCalled() {

        service.getByHolderId(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderId(1);
    }

    @Test
    public void getByHolderIdAndActive_Should_CallRepository_When_MethodCalled() {

        service.getByHolderIdAndActive(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderIdAndIsActiveTrue(1);
    }

    @Test
    public void getByHolderId_Should_Throw_When_CardDoesNotExist() {
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void create_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();

        service.create(mockCard, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void create_ShouldThrow_When_CardNumberNotUnique() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.existsByNumber(mockCard.getNumber())).thenReturn(true);

        Assertions.assertThrows(EntityDuplicateException.class, () -> service.create(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        service.update(mockCard, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void update_Should_Throw_When_CardDoesNotExist() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_Throw_When_UserNotAuthorized() {
        Card mockCard = Helpers.createMockCard();
        User holder = Helpers.createMockUser();
        holder.setId(2);
        mockCard.setHolder(holder);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        Assertions.assertThrows(AuthorizationException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_Throw_When_CardNumberNotUnique() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));
        Mockito.when(mockRepository.existsByNumber(mockCard.getNumber())).thenReturn(true);

        Assertions.assertThrows(EntityDuplicateException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void deactivate_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        service.deactivate(1, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void deactivate_Should_Throw_When_CardDoesNotExist() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deactivate(1, Helpers.createMockUser()));
    }

    @Test
    public void deactivate_Should_Throw_When_UserNotAuthorized() {
        Card mockCard = Helpers.createMockCard();
        User holder = Helpers.createMockUser();
        holder.setId(2);
        mockCard.setHolder(holder);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        Assertions.assertThrows(AuthorizationException.class, () -> service.deactivate(1, Helpers.createMockUser()));
    }

    @Test
    public void changeStatus_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        service.changeStatus(1, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void changeStatus_Should_Throw_When_CardDoesNotExist() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.changeStatus(1, Helpers.createMockUser()));
    }
    @Test
    public void changeStatus_Should_Throw_When_UserNotAuthorized() {
        Card mockCard = Helpers.createMockCard();
        User holder = Helpers.createMockUser();
        holder.setId(2);
        mockCard.setHolder(holder);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        Assertions.assertThrows(AuthorizationException.class, () -> service.changeStatus(1, Helpers.createMockUser()));
    }


    @Test
    public void delete_Should_CallRepository_When_MethodCalled() {
        service.delete(1);

        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(1);
    }
}
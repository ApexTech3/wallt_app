package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.clients.DummyCardClient;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.CardDetails;
import com.apex.tech3.wallt_app.repositories.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTests {
    @Mock
    TransferRepository mockRepository;
    @InjectMocks
    TransferServiceImpl service;
    @Mock
    DummyCardClient dummyCardClient;

    @Test
    public void deposit_Should_CallRepository_When_MethodCalled() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        CardDetails cardDetails = new CardDetails(mockTransfer.getCard().getNumber(),
                mockTransfer.getCard().getCardHolderName(), mockTransfer.getCard().getExpirationMonth(),
                mockTransfer.getCard().getExpirationYear(), mockTransfer.getCard().getCvv(), mockTransfer.getAmount());
        Mockito.when(dummyCardClient.tryPay(cardDetails)).thenReturn(true);
        service.deposit(mockTransfer, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockTransfer);
    }

    @Test
    public void deposit_Should_Throw_When_UserNotAuthorized() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        User mockUser = new User();
        mockUser.setId(2);
        mockUser.setUsername("MockUsername2");
        mockUser.setPassword("MockPassword2");
        mockUser.setFirstName("MockFirstName2");
        mockUser.setLastName("MockLastName2");
        mockUser.setEmail("mock@user.com");
        mockUser.setRoles(Set.of(Helpers.createMockUserRole()));

        Assertions.assertThrows(AuthorizationException.class, () -> service.deposit(mockTransfer, mockUser));
    }

    @Test
    public void initiateWithdraw_Should_CallRepository_When_MethodCalled() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        service.initiateWithdraw(mockTransfer, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockTransfer);
    }

    @Test
    public void editWithdraw_Should_CallRepository_When_MethodCalled() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        service.editWithdraw(mockTransfer, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockTransfer);
    }
    @Test
    public void confirmWithdraw_Should_CallRepository_When_MethodCalled() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        service.confirmWithdraw(mockTransfer, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockTransfer);
    }   @Test
    public void cancelWithdraw_Should_CallRepository_When_MethodCalled() {
        Transfer mockTransfer = Helpers.createMockTransfer();
        service.confirmWithdraw(mockTransfer, Helpers.createMockUser());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockTransfer);
    }
}
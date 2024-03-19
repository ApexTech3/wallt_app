package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.models.Currency;
import com.apex.tech3.wallt_app.models.Role;
import com.apex.tech3.wallt_app.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    @Mock
    RoleRepository mockRepository;
    @InjectMocks
    RoleServiceImpl mockService;


    @Test
    public void getByRole_Should_CallRepository_When_MethodCalled() {
        Role role = Helpers.createMockUserRole();
        Mockito.when(mockRepository.findByName(Mockito.anyString())).thenReturn(role);

        mockService.get(Mockito.anyString());

        Mockito.verify(mockRepository, Mockito.times(1)).findByName(Mockito.anyString());
    }

    @Test
    public void get_Should_CallRepository_When_MethodCalled() {
        Role role = Helpers.createMockUserRole();

        mockService.get();

        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }
}

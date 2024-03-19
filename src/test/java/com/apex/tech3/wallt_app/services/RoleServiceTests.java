package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @Mock
    RoleRepository mockRepository;
    @InjectMocks
    RoleServiceImpl service;

    @Test
    public void get_Should_CallRepository_When_MethodCalled() {
        service.get("USER");

        Mockito.verify(mockRepository, Mockito.times(1)).findByName("USER");
    } @Test
    public void getAll_Should_CallRepository_When_MethodCalled() {
        service.get();

        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }
}

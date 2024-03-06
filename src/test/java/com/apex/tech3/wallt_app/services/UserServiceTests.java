package com.apex.tech3.wallt_app.services;
import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.filters.UserSpecification;
import org.hibernate.query.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository mockRepository;
    @InjectMocks
    UserServiceImpl service;


    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockUser));

        service.getById(1);

        Mockito.verify(mockRepository, times(1)).findById(1);
    }

    @Test
    public void getByUsername_Should_CallRepository_When_MethodCalled() {
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByUsername("MockUsername")).thenReturn(mockUser);

        service.getByUsername("MockUsername");

        Mockito.verify(mockRepository, times(1)).findByUsername("MockUsername");
    }
}

package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.helpers.AuthenticationHelper;
import com.apex.tech3.wallt_app.helpers.UserMapper;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.models.dtos.UserResponseDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Register;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationHelper helper;

    public UserRestController(UserService userService, UserMapper userMapper, AuthenticationHelper helper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.helper = helper;
    }

    @PostMapping
    public UserResponseDto register(@Validated(Register.class) @RequestBody UserRegisterDto registerDto) {
        try {
            User user = userMapper.fromRegisterDto(registerDto);
            return userMapper.toResponseDto(userService.register(user));
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/{id}")
    public UserResponseDto updateInfo(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        try {
            User requester = helper.tryGetUser(headers);
            User user = userMapper.fromUpdateDto(userUpdateDto);
            return userMapper.toResponseDto(userService.update(user, requester));
        } catch (AuthorizationException | AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}

package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.exceptions.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable int id) {
        try {
            return UserMapper.toResponseDto(userService.get(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public Page<UserResponseDto> getAll(@RequestParam(required = false) Pageable pageable,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false) Integer id,
                                        @RequestParam(required = false) String firstName,
                                        @RequestParam(required = false) String middleName,
                                        @RequestParam(required = false) String lastName,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String phone) {
        return new PageImpl<>(userService.getAll(pageable, id, username, firstName, middleName, lastName, email, phone)
                .stream()
                .map(UserMapper::toResponseDto)
                .toList());
    }

    @PostMapping
    public HttpStatus register(@Validated(Register.class) @RequestBody UserRegisterDto registerDto) {
        try {
            User user = userMapper.fromRegisterDto(registerDto);
            userService.register(user);
            return HttpStatus.CONTINUE;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        try {
            userService.confirmUser(token);
            return ResponseEntity.ok("Email confirmed successfully.");
        } catch (EntityNotFoundException | InvalidTokenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/{id}")//todo
    public UserResponseDto updateInfo(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserUpdateDto userUpdateDto,
                                      @PathVariable int id) {
        try {
            User requester = helper.tryGetUser(headers);
            User newUser = userMapper.fromUpdateDto(userUpdateDto, id);
            return UserMapper.toResponseDto(userService.update(newUser, requester, userUpdateDto, id));
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/block/{userId}")
    public UserResponseDto blockUser(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User admin = helper.tryGetUser(headers);

            userService.blockUser(userId, admin);

            return UserMapper.toResponseDto(userService.get(userId));

        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/unblock/{userId}")
    public UserResponseDto unblockUser(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User admin = helper.tryGetUser(headers);
            userService.unblockUser(userId, admin);

            return UserMapper.toResponseDto(userService.get(userId));
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{userId}")
    public HttpStatus delete(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User requester = helper.tryGetUser(headers);
            userService.deleteUser(userId, requester);
            return HttpStatus.OK;
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/restore/{userId}")
    public UserResponseDto restore(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User requester = helper.tryGetUser(headers);
            return UserMapper.toResponseDto(userService.restoreUser(userId, requester));
        } catch (AuthorizationException | AuthenticationFailureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

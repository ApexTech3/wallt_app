package com.apex.tech3.wallt_app.controllers.rest;

import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
}

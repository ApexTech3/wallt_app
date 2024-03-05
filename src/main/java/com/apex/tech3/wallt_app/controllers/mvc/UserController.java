package com.apex.tech3.wallt_app.controllers.mvc;

import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getAll(Model model) {
        //model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String getSingleUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }
}

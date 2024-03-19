package com.apex.tech3.wallt_app.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordRecoveryDto {
    @NotEmpty(message = "Password can't be empty")
    //@Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[+\\-*&^])", message = "Password must contain at least one capital letter, one digit, and one special symbol")
    private String password;
    @NotEmpty(message = "Password confirmation can't be empty")
    private String passwordConfirmation;
    private String token;
}

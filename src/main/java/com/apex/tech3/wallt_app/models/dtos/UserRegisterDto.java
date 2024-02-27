package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[+\\-*&^])", message = "Password must contain at least one capital letter, one digit, and one special symbol")
    private String password;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Middle name cannot be empty")
    private String middleName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    private String profilePicture;
    @NotEmpty(message = "Address cannot be null")
    private Address address;
}

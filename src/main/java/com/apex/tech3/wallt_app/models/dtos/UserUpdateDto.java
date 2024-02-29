package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {

    private String username;
    private String currentPassword;
    private String password;
    private String passwordConfirmation;
    @NotEmpty(message = "First name can't be empty")
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols")
    private String firstName;
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols")
    @NotEmpty(message = "Middle name can't be empty")
    private String middleName;
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols")
    @NotEmpty(message = "Last name can't be empty")
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    private String profilePicture;
    //@NotEmpty(message = "Address cannot be null")
    private Address address;
}

package com.apex.tech3.wallt_app.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateDto {

    private String username;
    @NotEmpty(message = "Current Password can't be empty")
    private String currentPassword;
    private String newPassword;
    private String passwordConfirmation;
    @NotEmpty(message = "First name can't be empty")
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols")
    private String firstName;
    @NotEmpty(message = "Middle name can't be empty")
    private String middleName;
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols")
    @NotEmpty(message = "Last name can't be empty")
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    private MultipartFile profilePicture;
    private String profilePictureURL;
    @NotEmpty(message = "Address cannot be null")
    private String street;
    @NotNull(message = "Number cannot be null")
    private Integer number;
    @NotEmpty(message = "City cannot be null")
    private String city;
    @NotEmpty(message = "Country cannot be null")
    private String country;
    private boolean verified;
}

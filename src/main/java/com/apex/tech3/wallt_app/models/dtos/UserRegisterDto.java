package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.dtos.interfaces.Login;
import com.apex.tech3.wallt_app.models.dtos.interfaces.Register;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRegisterDto {
    @NotEmpty(message = "Username can't be empty", groups = {Register.class, Login.class})
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols", groups = {Register.class})
    private String username;
    @NotEmpty(message = "Password can't be empty", groups = {Register.class, Login.class})
    //@Size(min = 8, message = "Password must be at least 8 characters long", groups = {Register.class})
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\-]).{8,}$", message = "Password must contain at least one capital letter, one digit, and one special symbol", groups = {Register.class})
    private String password;
    private String passwordConfirmation;
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
    private String profilePictureURL;
    private MultipartFile profilePicture;
    @NotEmpty(message = "Address cannot be null")
    private String street;
    @NotEmpty
    private int number;
    @NotEmpty(message = "City cannot be null")
    private String city;
    @NotEmpty(message = "Country cannot be null")
    private String country;

}

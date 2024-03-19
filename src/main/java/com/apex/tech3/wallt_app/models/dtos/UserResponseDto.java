package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.Role;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class UserResponseDto {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private int number;
    private String city;
    private String country;
    private String profilePicture;
    private Set<Role> roles;
    private boolean isBlocked;
    private boolean isVerified;
    private Timestamp stampCreated;

}

package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.models.dtos.UserResponseDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.services.contracts.RoleService;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UserMapper(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public User fromRegisterDto(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        user.setFirstName(userRegisterDto.getFirstName());
        user.setMiddleName(userRegisterDto.getMiddleName());
        user.setLastName(userRegisterDto.getLastName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPhone(userRegisterDto.getPhone());
        if (user.getRoles() == null) {
            user.setRoles(Set.of(roleService.get("USER")));
        }
        if (userRegisterDto.getProfilePictureURL() != null) {
            user.setProfilePicture(userRegisterDto.getProfilePictureURL());
        }
        user.setStreet(userRegisterDto.getStreet());
        user.setNumber(userRegisterDto.getNumber());
        user.setCity(userRegisterDto.getCity());
        user.setCountry(userRegisterDto.getCountry());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setStreet(user.getStreet());
        userResponse.setNumber(userResponse.getNumber());
        userResponse.setCity(userResponse.getCity());
        userResponse.setCountry(user.getCountry());
        user.setProfilePicture(user.getProfilePicture());
        userResponse.setRoles(user.getRoles());
        userResponse.setBlocked(user.isBlocked());
        userResponse.setVerified(user.isVerified());
        userResponse.setStampCreated(user.getStampCreated());
        return userResponse;
    }

    public User fromUpdateDto(UserUpdateDto userUpdateDto, int id) {
        User user = userService.getById(id);
        user.setPassword(userUpdateDto.getCurrentPassword());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        user.setLastName(userUpdateDto.getLastName());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhone(userUpdateDto.getPhone());
        if (userUpdateDto.getProfilePictureURL() != null) {
            user.setProfilePicture(userUpdateDto.getProfilePictureURL());
        }
        user.setStreet(userUpdateDto.getStreet());
        user.setNumber(userUpdateDto.getNumber());
        user.setCity(userUpdateDto.getCity());
        user.setCountry(userUpdateDto.getCountry());
        return user;
    }

    public UserUpdateDto toUpdateDto(User user) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername(user.getUsername());
        userUpdateDto.setFirstName(user.getFirstName());
        userUpdateDto.setMiddleName(user.getFirstName());
        userUpdateDto.setLastName(user.getLastName());
        userUpdateDto.setEmail(user.getEmail());
        userUpdateDto.setPhone(user.getPhone());
        userUpdateDto.setStreet(user.getStreet());
        userUpdateDto.setNumber(user.getNumber());
        userUpdateDto.setCity(user.getCity());
        userUpdateDto.setCountry(user.getCountry());
        userUpdateDto.setProfilePictureURL(user.getProfilePicture());
        userUpdateDto.setVerified(user.isVerified());
        return userUpdateDto;
    }
}

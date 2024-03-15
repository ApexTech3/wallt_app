package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.models.dtos.UserResponseDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.services.contracts.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final RoleService roleService;

    @Autowired
    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
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
        user.getRoles().add(roleService.get("USER"));
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
        User user = new User();
        user.setId(id);
        user.setUsername(userUpdateDto.getUsername());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        user.setLastName(userUpdateDto.getLastName());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhone(userUpdateDto.getPhone());
        user.setProfilePicture(userUpdateDto.getProfilePicture());
        user.setStreet(userUpdateDto.getStreet());
        user.setNumber(userUpdateDto.getNumber());
        user.setCity(userUpdateDto.getCity());
        user.setCountry(userUpdateDto.getCountry());
        return user;
    }

}

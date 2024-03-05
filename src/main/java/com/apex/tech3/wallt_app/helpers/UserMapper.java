package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.Address;
import com.apex.tech3.wallt_app.models.City;
import com.apex.tech3.wallt_app.models.Country;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.models.dtos.UserRegisterDto;
import com.apex.tech3.wallt_app.models.dtos.UserResponseDto;
import com.apex.tech3.wallt_app.models.dtos.UserUpdateDto;
import com.apex.tech3.wallt_app.models.dtos.interfaces.UserRequestDto;
import com.apex.tech3.wallt_app.repositories.AddressRepository;
import com.apex.tech3.wallt_app.repositories.CityRepository;
import com.apex.tech3.wallt_app.repositories.CountryRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;

    public UserMapper(CountryRepository countryRepository, CityRepository cityRepository, AddressRepository addressRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
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
        user.setProfilePicture(userRegisterDto.getProfilePicture());
        user.setAddress(addressExtract(userRegisterDto));
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
        userResponse.setAddress(user.getAddress());
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
        user.setAddress(addressExtract(userUpdateDto));
        return user;
    }

    Address addressExtract(UserRequestDto userRequestDto) {
        String street = userRequestDto.getStreet();
        int number = userRequestDto.getNumber();
        City city = cityRepository.getByName(userRequestDto.getCity());
        Country country = countryRepository.getByName(userRequestDto.getCountry());
        city.setCountry(country);
        cityRepository.save(city);
        countryRepository.save(country);
        Address address = new Address();
        address.setNumber(number);
        address.setStreet(street);
        address.setCity(city);
        addressRepository.save(address);
        return address;
    }

}

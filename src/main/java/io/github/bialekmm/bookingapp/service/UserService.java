package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
    UserEntity findByEmail(String email);
    void saveUser(UserDto userDto);
    void deleteUser(Long id);

}

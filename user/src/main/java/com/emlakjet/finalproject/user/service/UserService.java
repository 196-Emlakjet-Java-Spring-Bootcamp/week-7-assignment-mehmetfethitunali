package com.emlakjet.finalproject.user.service;

import com.emlakjet.finalproject.user.dto.UserDto;
import com.emlakjet.finalproject.user.dto.UserSummary;
import com.emlakjet.finalproject.user.entity.User;

import java.util.Optional;

public interface UserService {

    boolean createUser(UserDto userDto);

    boolean deleteUser(UserDto userDto);

    boolean updateUser(UserDto userDto);

    Optional<User> getUser(Long id);

    UserSummary transferUserSummary(Long id);
}

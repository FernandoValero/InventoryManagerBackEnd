package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer id);
    void deleteUser(Integer id);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUsers();
}

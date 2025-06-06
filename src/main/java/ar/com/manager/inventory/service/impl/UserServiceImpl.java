package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.UserDto;
import ar.com.manager.inventory.entity.User;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.mapper.UserMapper;
import ar.com.manager.inventory.repository.UserRepository;
import ar.com.manager.inventory.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto addUser(UserDto userDto) throws ValidationException {
        if(userRepository.existsByUserName(userDto.getUserName())){
            throw new ValidationException("The user username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new ValidationException("The user email already exists");
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())){
            throw new ValidationException("The user phone number already exists");
        }
        User user = userMapper.toEntity(userDto);
        user.setDeleted(false);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) throws ValidationException, NotFoundException {
        if(userDto == null){
            throw new ValidationException("The user cannot be null");
        }
        User user = userRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "The user with id " + id + " does not exist.";
            return new NotFoundException(errorMessage);
        });
        if(userRepository.existsByUserNameAndIdNot(userDto.getUserName(), id)){
            throw new ValidationException("The user UserName already exists");
        }
        if(userRepository.existsByEmailAndIdNot(userDto.getEmail(), id)){
            throw new ValidationException("The user Email already exists");
        }
        if(userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(), id)){
            throw new ValidationException("The user PhoneNumber already exists");
        }
        setUser(userDto, user);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("The user with id " + id + " does not exist.");
        }
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null|| user.getDeleted()){
            throw new NotFoundException("The user with id " + id + " does not exist.");
        }
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findByDeletedFalse()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setUser(UserDto modifiedUser, User finalUser) {
        finalUser.setFirstName(modifiedUser.getFirstName());
        finalUser.setLastName(modifiedUser.getLastName());
        finalUser.setUserName(modifiedUser.getUserName());
        finalUser.setPhoneNumber(modifiedUser.getPhoneNumber());
        finalUser.setEmail(modifiedUser.getEmail());
        finalUser.setType(finalUser.getType());
        finalUser.setEnabled(modifiedUser.isEnabled());
    }
}

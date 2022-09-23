package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.UserMapperDto;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.dao.UserStorageDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapperDto userMapperDto;
    private final UserStorageDAO storageDAO;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.debug("Got userDto create userMapper: {}", userDto);
        if (userDto.getAge() == 0 || userDto.getTitle() == null || userDto.getFullName() == null) {
            throw new UserNotFoundException(userDto);
        }
        User userMapper = userMapperDto.userDtoYoUser(userDto);
        log.debug("Mapped userDto: {}", userMapper);
        User user = storageDAO.createUser(userMapper);
        log.debug("Created user: {}", user);

        return userMapperDto.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.debug("Got userDto update userMapper: {}", userDto);
        if (userDto == null) {
            throw new UserNotFoundException(userDto);
        }
        User userMapper = userMapperDto.userDtoYoUser(userDto);
        log.debug("Mapped userDto: {}", userMapper);
        User userUpdated = storageDAO.updateUser(userMapper);
        log.debug("Update userUpdated: {}", userUpdated);

        return userMapperDto.userToUserDto(userUpdated);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.debug("Got id get user by id: {}", id);
        if (storageDAO.getUserById(id) == null) {
            throw new UserNotFoundException(id);
        }
        User user = storageDAO.getUserById(id);
        log.debug("Get user by id: {}", user);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return userMapperDto.userToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        log.debug("Got id delete user by id: {}", id);
        if (storageDAO.getUserById(id) != null) {
            storageDAO.deleteUserById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<Long> getBooksByUserId(Long id) {
        if (id == null) {
            throw new UserNotFoundException(id);
        }
        User user = storageDAO.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(user);
        }

        return user.getBooks();
    }
}

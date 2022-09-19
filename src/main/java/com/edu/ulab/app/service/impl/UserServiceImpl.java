package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.UserMapperDto;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.dao.UserStorageDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private final UserMapperDto userMapperDto;
    private final UserStorageDAO storageDAO;

    @Autowired
    public UserServiceImpl(UserMapperDto userMapperDto, UserStorageDAO storageDAO) {
        this.userMapperDto = userMapperDto;
        this.storageDAO = storageDAO;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.debug("Got userDto create userMapper: {}", userDto);
        User userMapper = userMapperDto.userDtoYoUser(Objects.requireNonNull(userDto,"Not found userDto"));
        log.debug("Mapped userDto: {}", userMapper);
        User user = storageDAO.createUser(userMapper);
        log.debug("Created user: {}", user);

        return userMapperDto.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.debug("Got userDto update userMapper: {}", userDto);
        User userMapper = userMapperDto.userDtoYoUser(Objects.requireNonNull(userDto, "Not found userDto"));
        log.debug("Mapped userDto: {}", userMapper);
        User userUpdated = storageDAO.updateUser(userMapper);
        log.debug("Update userUpdated: {}", userUpdated);

        return userMapperDto.userToUserDto(userUpdated);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.debug("Got id get user by id: {}", id);
        User user = storageDAO.getUserById(id);
        log.debug("Get user by id: {}", user);

        return userMapperDto.userToUserDto(Objects.requireNonNull(user, "Not found user"));
    }

    @Override
    public void deleteUserById(Long id) {
        log.debug("Got id delete user by id: {}", id);
        storageDAO.deleteUserById(id);
    }

    @Override
    public List<Long> getBooksByUserId(Long id) {
        User user = storageDAO.getUserById(id);

        return Objects.requireNonNull(user.getBooks());
    }
}

package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.UserMapperDto;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.impl.UserStorageImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private final UserMapperDto userMapperDto;
    private final UserStorageImp userStorage;

    @Autowired
    public UserServiceImpl(UserMapperDto userMapperDto, UserStorageImp userStorage) {
        this.userMapperDto = userMapperDto;
        this.userStorage = userStorage;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.debug("Got userDto create userMapper: {}", userDto);
        User userMapper = userMapperDto.userDtoYoUser(Objects.requireNonNull(userDto,"Not found userDto"));
        log.debug("Mapped userDto: {}", userMapper);
        User user = userStorage.create(userMapper);
        log.debug("Created user: {}", user);
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        return userMapperDto.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.debug("Got userDto update userMapper: {}", userDto);
        User userMapper = userMapperDto.userDtoYoUser(Objects.requireNonNull(userDto, "Not found userDto"));
        log.debug("Mapped userDto: {}", userMapper);
        User userUpdated = userStorage.update(userMapper);
        log.debug("Update userUpdated: {}", userUpdated);

        return userMapperDto.userToUserDto(userUpdated);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.debug("Got id get user by id: {}", id);
        User user = userStorage.get(id);
        log.debug("Get user by id: {}", user);

        return userMapperDto.userToUserDto(Objects.requireNonNull(user, "Not found user"));
    }

    @Override
    public void deleteUserById(Long id) {
        log.debug("Got id delete user by id: {}", id);
        userStorage.delete(id);
    }
}

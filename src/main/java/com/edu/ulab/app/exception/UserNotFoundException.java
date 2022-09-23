package com.edu.ulab.app.exception;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(User user) {
        super("Not found user: " + user.toString());
    }

    public UserNotFoundException(UserDto userDto) {
        super("Not found userDto: " + userDto.toString());
    }

    public UserNotFoundException(Long id) {
        super("Not found user with id: " + id);
    }
}

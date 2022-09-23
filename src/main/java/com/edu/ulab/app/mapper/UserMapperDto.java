package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDto {

    UserDto userToUserDto(User user);

    User userDtoYoUser(UserDto userDto);
}

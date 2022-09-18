package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserServiceImpl userServiceImpl;
    private final BookServiceImpl bookServiceImpl;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserServiceImpl userService,
                          BookServiceImpl bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userServiceImpl = userService;
        this.bookServiceImpl = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.debug("Got user book create request: {}", userBookRequest);

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.debug("Mapped user request: {}", userDto);
        UserDto createdUser = userServiceImpl.createUser(userDto);
        log.debug("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookServiceImpl::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.debug("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest) {
        log.debug("Got user book update request: {}", userBookRequest);

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.debug("Mapped user request: {}", userDto);
        UserDto userUpdated = userServiceImpl.updateUser(userDto);
        log.debug("Updated user: {}", userUpdated);

        List<Long> bookUpdated = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userUpdated.getId()))
                .peek(mappedBookDto -> log.debug("mapped book: {}", mappedBookDto))
                .map(bookServiceImpl::updateBook)
                .peek(updateBook -> log.debug("Updated book: {}", updateBook))
                .map(BookDto::getId)
                .toList();
        log.debug("Collected book updated: {}", bookUpdated);

        return UserBookResponse.builder()
                .userId(userUpdated.getId())
                .booksIdList(bookUpdated)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.debug("Got user id get user with books: {}", userId);

        UserDto userById = userServiceImpl.getUserById(userId);
        log.debug("Get user by id: {}", userById);
        List<Long> books = bookServiceImpl.getBooks();
        log.debug("Get books by user: {}", books);

        return UserBookResponse.builder()
                .userId(userById.getId())
                .booksIdList(books)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.debug("Got id delete user by id: {}", userId);
        userServiceImpl.deleteUserById(userId);
    }
}

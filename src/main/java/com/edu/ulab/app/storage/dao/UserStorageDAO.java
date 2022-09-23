package com.edu.ulab.app.storage.dao;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class UserStorageDAO {

    private final UserStorage userStorage;

    private Long userId = 0L;

    @Autowired
    public UserStorageDAO(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        log.debug("Got create user: {}", user);
        user.setId(++userId);
        user.setBooks(new ArrayList<>());
        userStorage.saveUser(userId, user);

        return user;
    }

    public User getUserById(Long id) {
        log.debug("Got user id: {}", id);
        return userStorage.getUser(id);
    }

    public User updateUser(User user) {
        log.debug("Got update user: {}", user);
        return userStorage.updateUser(user);
    }

    public void deleteUserById(Long id) {
        log.debug("Got delete user id: {}", id);
        userStorage.deleteUser(id);
    }
}

package com.edu.ulab.app.storage.dao;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
@Slf4j
public class UserStorageDAO {

    private final Storage storage;

    private Long userId = 1L;

    @Autowired
    public UserStorageDAO(Storage storage) {
        this.storage = storage;
    }

    public User createUser(User user) {
        log.debug("Got create user: {}", user);
        user.setId(++userId);
        user.setBooks(new ArrayList<>());
        storage.save(userId, Objects.requireNonNull(user));

        return Objects.requireNonNull(user);
    }

    public User getUserById(Long id) {
        log.debug("Got user id: {}", id);
        return storage.getUser(id);
    }

    public User updateUser(User user) {
        log.debug("Got update user: {}", user);
        return storage.updateUser(Objects.requireNonNull(user));
    }

    public void deleteUserById(Long id) {
        log.debug("Got delete user id: {}", id);
        if (storage.getUser(id) != null) {
            storage.deleteUser(id);
        } else {
            throw new NotFoundException("User with id: " + id + " not found.");
        }
    }
}

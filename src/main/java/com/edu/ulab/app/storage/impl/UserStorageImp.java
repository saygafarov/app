package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserStorageImp implements Storage<Long, User> {

    private BookStorageImpl bookStorage;

    private final Map<Long, User> userStorage = new HashMap<>();
    private final Map<User, List<Long>> userBooks = new HashMap<>();
    private Long userId = 0L;


    @Override
    public User create(User user) {
        userStorage.put(++userId, Objects.requireNonNull(user));
        userBooks.put(Objects.requireNonNull(user), bookStorage.getBooks());

        return Objects.requireNonNull(user);
    }

    @Override
    public User get(Long id) {
        User user = userStorage.entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(value -> id.equals(value.getKey()))
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow();

        user.setBooks(userBooks.get(user));

        return user;
    }

    @Override
    public User update(User user) {
        if (user != null && userStorage.containsValue(user)) {
            userStorage.remove(user.getId());
            userBooks.remove(user);
            userStorage.put(++userId, user);
            userBooks.put(user, user.getBooks());
        } else {
            throw new NotFoundException("User: " + user + " not found.");
        }

        return get(Objects.requireNonNull(user).getId());
    }

    @Override
    public void delete(Long id) {
        if (userStorage.get(id) != null) {
            userStorage.remove(id);
            userBooks.remove(get(id));
        } else {
            throw new NotFoundException("User with id: " + id + " not found.");
        }
    }
}

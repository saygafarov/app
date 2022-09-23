package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorage {

    private final Map<Long, User> userStorage = new HashMap<>();

    private final BookStorage bookStorage;

    @Autowired
    public UserStorage(@Lazy BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    public void saveUser(Long id, User user) {
        userStorage.put(id, user);
    }

    public User getUser(Long id) {
        User user = userStorage.get(id);
        user.setBooks(user.getBooks()
                .stream()
                .filter(book -> bookStorage.getBookStorage().get(book) != null)
                .toList());

        return user;
    }

    public User updateUser(User user) {
        userStorage.remove(user.getId());
        userStorage.put(user.getId(), user);

        return user;
    }

    public void deleteUser(Long id) {
        userStorage.remove(id);
    }

    public Map<Long, User> getUserStorage() {
        return userStorage;
    }
}

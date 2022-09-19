package com.edu.ulab.app.storage;


//todo создать хранилище в котором будут содержаться данные
// сделать абстракции через которые можно будет производить операции с хранилищем
// продумать логику поиска и сохранения
// продумать возможные ошибки
// учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
// продумать что у узера может быть много книг и нужно создать эту связь
// так же учесть, что методы хранилища принимают другой тип данных - учесть это в абстракции

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Storage {
    private final Map<Long, User> userStorage = new HashMap<>();
    private final Map<Long, Book> bookStorage = new HashMap<>();

    public void save(Long id, User user) {
        userStorage.put(id, user);
    }
    public void save(Long id, Book book) {
        userStorage.get(book.getUserId()).getBooks().add(id);
        bookStorage.put(id, book);
    }

    public User getUser(Long id) {
        User user = userStorage.get(id);
        user.setBooks(user.getBooks().stream().filter(book -> bookStorage.get(book) != null).toList());

        return user;
    }

    public Book getBook(Long id) {
        return bookStorage.get(id);
    }
    public User updateUser(User user) {
        userStorage.remove(user.getId());
        userStorage.put(user.getId(), user);

        return user;
    }

    public Book updateBook(Book book) {
        bookStorage.remove(book.getId());
        bookStorage.put(book.getId(), book);

        return book;
    }

    public void deleteUser(Long id) {
        userStorage.remove(id);
    }

    public void deleteBook(Long id) {
        bookStorage.remove(id);
    }
}

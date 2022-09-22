package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookStorage {

    private final Map<Long, Book> bookStorage = new HashMap<>();

    private final UserStorage userStorage;

    @Autowired
    public BookStorage(@Lazy UserStorage storage) {
        this.userStorage = storage;
    }

    public void saveBook(Long id, Book book) {
        userStorage.getUserStorage().get(book.getUserId()).getBooks().add(id);
        bookStorage.put(id, book);
    }

    public Book getBook(Long id) {
        return bookStorage.get(id);
    }

    public Book updateBook(Book book) {
        bookStorage.remove(book.getId());
        bookStorage.put(book.getId(), book);

        return book;
    }

    public void deleteBook(Long id) {
        bookStorage.remove(id);
    }

    public Map<Long, Book> getBookStorage() {
        return bookStorage;
    }
}

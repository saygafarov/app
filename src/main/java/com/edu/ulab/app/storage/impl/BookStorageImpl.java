package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BookStorageImpl implements Storage<Long, Book> {

    private final Map<Long, Book> bookStorage = new HashMap<>();

    private Long bookId = 0L;

    @Override
    public Book create(Book book) {
        bookStorage.put(++bookId, Objects.requireNonNull(book));

        return Objects.requireNonNull(book);
    }

    @Override
    public Book get(Long id) {
        return bookStorage.entrySet()
                .stream()
                .filter(value -> id.equals(value.getKey()))
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow();
    }

    @Override
    public Book update(Book book) {
        if (book != null && bookStorage.containsValue(book)) {
            bookStorage.remove(book.getId());
            bookStorage.put(++bookId, book);
        } else {
            throw new NotFoundException("book: " + book + " not found.");
        }
        return get(Objects.requireNonNull(book).getId());
    }

    @Override
    public void delete(Long id) {
        if (bookStorage.get(id) != null) {
            bookStorage.remove(id);
        } else {
            throw new NotFoundException("book with id: " + id + " not found.");
        }
    }

    public List<Long> getBooks() {
        return bookStorage
                .keySet()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

}

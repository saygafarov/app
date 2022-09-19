package com.edu.ulab.app.storage.dao;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
@Slf4j
public class BookStorageDAO {

    private final Storage storage;

    private Long bookId = 1L;

    @Autowired
    public BookStorageDAO(Storage storage) {
        this.storage = storage;
    }

    public Book createBook(Book book) {
        log.debug("Got create book: {}", book);
        book.setId(++bookId);
        storage.save(bookId, Objects.requireNonNull(book));

        return Objects.requireNonNull(book);
    }

    public Book getBookById(Long id) {
        log.debug("Got book id: {}", id);

        return Objects.requireNonNull(storage.getBook(id));
    }

    public Book updateBook(Book book) {
        log.debug("Got update book: {}", book);

        return storage.updateBook(Objects.requireNonNull(book));
    }

    public void deleteBookById(Long id) {
        log.debug("Got delete book id: {}", id);
        if (storage.getBook(id) != null) {
            storage.deleteBook(id);
        } else {
            throw new NotFoundException("book with id: " + id + " not found.");
        }
    }

}

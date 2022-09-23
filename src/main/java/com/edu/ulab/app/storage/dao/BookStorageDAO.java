package com.edu.ulab.app.storage.dao;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.BookStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookStorageDAO {

    private final BookStorage bookStorage;

    private Long bookId = 0L;

    @Autowired
    public BookStorageDAO(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    public Book createBook(Book book) {
        log.debug("Got create book: {}", book);
        book.setId(++bookId);
        bookStorage.saveBook(bookId, book);

        return book;
    }

    public Book getBookById(Long id) {
        log.debug("Got book id: {}", id);

        return bookStorage.getBook(id);
    }

    public Book updateBook(Book book) {
        log.debug("Got update book: {}", book);

        return bookStorage.updateBook(book);
    }

    public void deleteBookById(Long id) {
        log.debug("Got delete book id: {}", id);
        bookStorage.deleteBook(id);
    }
}

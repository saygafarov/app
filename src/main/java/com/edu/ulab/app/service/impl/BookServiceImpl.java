package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.mapper.BookMapperDto;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.dao.BookStorageDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapperDto bookMapperDto;
    private final BookStorageDAO storageDAO;

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.debug("Got bookDto create bookMapper: {}", bookDto);
        if (bookDto.getAuthor() == null || bookDto.getTitle() == null || bookDto.getPageCount() == 0) {
            throw new BookNotFoundException(bookDto);
        }
        Book bookMapper = bookMapperDto.bookDtoToBook(bookDto);
        log.debug("Mapped bookDto: {}", bookMapper);
        Book book = storageDAO.createBook(bookMapper);
        log.debug("Created book: {}", book);

        return bookMapperDto.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.debug("Got bookDto update bookMapper: {}", bookDto);
        if (bookDto == null) {
            throw new BookNotFoundException(bookDto);
        }
        Book bookMapper = bookMapperDto.bookDtoToBook(bookDto);
        log.debug("Mapped bookDto: {}", bookMapper);
        Book bookUpdate = storageDAO.updateBook(bookMapper);
        log.debug("Update bookUpdate: {}", bookUpdate);

        return bookMapperDto.bookToBookDto(bookUpdate);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.debug("Got id get book by id: {}", id);
        Book book = storageDAO.getBookById(id);
        log.debug("Get book by id: {}", book);
        if (book == null) {
            throw new BookNotFoundException(id);
        }

        return bookMapperDto.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        log.debug("Got id delete book by id: {}", id);
        if (storageDAO.getBookById(id) != null) {
            storageDAO.deleteBookById(id);
        } else {
            throw new BookNotFoundException(id);
        }
    }
}

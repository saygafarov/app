package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapperDto;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.dao.BookStorageDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookMapperDto bookMapperDto;
    private final BookStorageDAO storageDAO;

    @Autowired
    public BookServiceImpl(BookMapperDto bookMapperDto, BookStorageDAO storageDAO) {
        this.bookMapperDto = bookMapperDto;
        this.storageDAO = storageDAO;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.debug("Got bookDto create bookMapper: {}", bookDto);
        Book bookMapper = bookMapperDto.bookDtoToBook(Objects.requireNonNull(bookDto, "Not found bookDto"));
        log.debug("Mapped bookDto: {}", bookMapper);
        Book book = storageDAO.createBook(bookMapper);
        log.debug("Created book: {}", book);

        return bookMapperDto.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.debug("Got bookDto update bookMapper: {}", bookDto);
        Book bookMapper = bookMapperDto.bookDtoToBook(Objects.requireNonNull(bookDto, "Not found bookDto"));
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

        return bookMapperDto.bookToBookDto(Objects.requireNonNull(book, "Not found book"));
    }

    @Override
    public void deleteBookById(Long id) {
        log.debug("Got id delete book by id: {}", id);
        storageDAO.deleteBookById(id);
    }

}

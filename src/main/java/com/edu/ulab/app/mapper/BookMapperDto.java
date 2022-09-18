package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapperDto {

    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
}

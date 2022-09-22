package com.edu.ulab.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}

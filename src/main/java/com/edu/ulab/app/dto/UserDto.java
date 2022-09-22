package com.edu.ulab.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String title;
    private int age;
    private List<Long> books;
}

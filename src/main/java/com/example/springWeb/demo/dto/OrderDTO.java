package com.example.springWeb.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long userId;
    private String userName;
    private String email;
    private long bookId;
    private String bookName;
    private String author;
}

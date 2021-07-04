package com.example.springWeb.demo.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private long id_user;
    private String name_user;
    private String email;
    private long id_book;
    private String name_book;
    private String author;
}

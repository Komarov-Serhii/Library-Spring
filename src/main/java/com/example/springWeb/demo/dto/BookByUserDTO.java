package com.example.springWeb.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookByUserDTO {
    private String name;
    private String author;
    private String publisher;
    private String publisherDate;
    private String description;
    private String genre;
    private int price;
    private int debt;
    private Date returnDate;
}

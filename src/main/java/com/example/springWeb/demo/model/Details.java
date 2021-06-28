package com.example.springWeb.demo.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity

@Data
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;
    private String publisher;
    @Column(name="publisher_date")
    private String publisherDate;
    private String description;
    private int price;
    private String genre;


    public Details() {

    }
}

package com.example.springWeb.demo.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Book {

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @OneToOne
    @JoinColumn(name = "details_id")
    private Details details;
    private boolean active;

}

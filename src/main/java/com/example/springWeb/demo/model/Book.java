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
    @Column(name="return_date")
    private Date date;
    private int debt;
    @OneToOne
    @JoinColumn(name = "details_id")
    private Details details;
    private boolean active;

}

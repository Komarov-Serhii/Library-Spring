package com.example.springWeb.demo.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Order() {
    }
}

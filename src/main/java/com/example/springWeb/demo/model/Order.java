package com.example.springWeb.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name="t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean status;

    @Column(name="return_date")
    private Date date;
    private int debt;

    public Order() {
    }
}

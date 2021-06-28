package com.example.springWeb.demo.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean active;

    public Person() {

    }
}

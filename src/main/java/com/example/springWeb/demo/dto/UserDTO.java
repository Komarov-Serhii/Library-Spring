package com.example.springWeb.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private boolean active;
}

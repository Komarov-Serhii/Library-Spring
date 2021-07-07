package com.example.springWeb.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForProfileDTO {
    private String name;
    private String email;
    private String oldPassword;
    private String newPassword;
}

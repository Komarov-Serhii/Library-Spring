package com.example.springWeb.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoBookAndUserDTO {

   private int countUser;
   private int countActiveUser;
   private int countBlockedUser;
   private int countBook;
   private int countBusyBook;
   private int countOrderBook;

}

package com.example.springWeb.demo.dto;


import lombok.Data;

@Data
public class InfoBookAndUserDTO {

   private int countUser;
   private int countActiveUser;
   private int countBlockedUser;
   private int countBook;
   private int countBusyBook;
   private int countOrderBook;

}

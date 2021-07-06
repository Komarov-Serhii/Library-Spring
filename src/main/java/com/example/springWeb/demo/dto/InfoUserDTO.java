package com.example.springWeb.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoUserDTO {

    private int contBook;
    private int contOrder;
    private int contDebt;
}

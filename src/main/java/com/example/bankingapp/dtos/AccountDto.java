package com.example.bankingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double balance;
}

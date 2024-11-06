package com.example.bankingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferFundDto {
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
}

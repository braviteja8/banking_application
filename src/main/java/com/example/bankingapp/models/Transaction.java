package com.example.bankingapp.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction extends BaseModel{
    private Long accountId;
    private double amount;
    /*
    Deposit/withdraw/transfer
     */
    private String transactionType;
    private LocalDateTime timeStamp;
}

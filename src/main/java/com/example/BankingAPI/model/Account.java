package com.example.BankingAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Account {
    @Id
    private long id;

    private String accountType;

    private String accountNumber;

    private double currentBalance;

    private String bankName;

    private Customer customer;

    private  List<Transaction> transactions;


}

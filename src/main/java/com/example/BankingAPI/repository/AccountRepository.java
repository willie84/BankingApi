package com.example.BankingAPI.repository;

import com.example.BankingAPI.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByAccountTypeAndAccountNumber(String accountType, String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);
}

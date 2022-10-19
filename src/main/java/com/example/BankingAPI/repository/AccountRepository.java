package com.example.BankingAPI.repository;

import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByAccountTypeAndAccountNumber(String accountType, String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<List<Account>> findAccountByCustomerAndBankName(Customer accountNumber,String BankName);
}

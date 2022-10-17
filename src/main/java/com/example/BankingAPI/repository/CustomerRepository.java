package com.example.BankingAPI.repository;

import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByidNumber(String idNumber);
}

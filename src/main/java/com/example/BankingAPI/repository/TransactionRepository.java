package com.example.BankingAPI.repository;

import com.example.BankingAPI.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findBySourceAccountIdOrderByInitiationDate(String id);
    Optional< List<Transaction>> findAllBySourceAccountId(String accountNumber);
}

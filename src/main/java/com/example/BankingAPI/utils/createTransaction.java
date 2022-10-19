package com.example.BankingAPI.utils;

import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.LedgerAmount;
import com.example.BankingAPI.model.Transaction;
import com.example.BankingAPI.repository.TransactionRepository;

import java.time.LocalDateTime;

public class createTransaction {

    public static Transaction createTransferTransaction(Account sourceAccount, Account targetAccount, LedgerAmount transcationAmounts, TransactionRepository transactionRepository, String reference) {
        var transaction = new Transaction();
        transaction.setAmount(new LedgerAmount("ZAR", transcationAmounts.getAmount()));
        transaction.setSourceAccountId(sourceAccount.getAccountNumber());
        transaction.setTargetAccountId(targetAccount.getAccountNumber());
        transaction.setTargetOwnerName(targetAccount.getCustomer());
        transaction.setInitiationDate(LocalDateTime.now());
        transaction.setCompletionDate(LocalDateTime.now());
        transaction.setReference(reference);
        transactionRepository.insert(transaction);
        return transaction;
    }

    public static Transaction createSingleTransaction(Account sourceAccount, LedgerAmount transcationAmounts, TransactionRepository transactionRepository, String reference) {
        var transaction = new Transaction();
        transaction.setAmount(new LedgerAmount("ZAR", transcationAmounts.getAmount()));
        transaction.setSourceAccountId(sourceAccount.getAccountNumber());
        transaction.setTargetAccountId(null);
        transaction.setTargetOwnerName(sourceAccount.getCustomer());
        transaction.setInitiationDate(LocalDateTime.now());
        transaction.setCompletionDate(LocalDateTime.now());
        transaction.setReference(reference);
        transactionRepository.insert(transaction);
        return transaction;
    }

}

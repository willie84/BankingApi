package com.example.BankingAPI.service;

import com.example.BankingAPI.constants.ACCOUNT_TYPE;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.Customer;
import com.example.BankingAPI.model.LedgerAmount;
import com.example.BankingAPI.repository.AccountRepository;
import com.example.BankingAPI.repository.TransactionRepository;
import com.mifmif.common.regex.Generex;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.BankingAPI.constants.constants.ACCOUNT_NUMBER_PATTERN_STRING;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccount(String accountType, String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountTypeAndAccountNumber(accountType, accountNumber);

        account.ifPresent(value ->
                value.setTransactions(transactionRepository
                        .findBySourceAccountIdOrderByInitiationDate(value.getAccountId())));

        return account.orElse(null);
    }

    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);
        return account.orElse(null);
    }

    public Account createAccount(String bankName, Customer customer) {
        Generex accountNumberGenerex = new Generex(ACCOUNT_NUMBER_PATTERN_STRING);
        LedgerAmount ledgerAmount = new LedgerAmount("ZAR",0.0);
        Account newAccount = new Account(ACCOUNT_TYPE.CHEQUE.toString(),accountNumberGenerex.random(), ledgerAmount,bankName,customer);
        return accountRepository.insert(newAccount);
    }
}

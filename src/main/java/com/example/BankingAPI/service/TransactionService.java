package com.example.BankingAPI.service;


import com.example.BankingAPI.constants.ACTION;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.LedgerAmount;
import com.example.BankingAPI.model.Transaction;
import com.example.BankingAPI.repository.AccountRepository;
import com.example.BankingAPI.repository.TransactionRepository;
import com.example.BankingAPI.utils.TransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean makeTransfer(TransactionDetails transactionDetails) {
        String accountType = transactionDetails.getSourceAccount().getAccountType();
        String sourceAccountNumber = transactionDetails.getSourceAccount().getAccountNumber();
        Optional<Account> sourceAccount = accountRepository
                .findByAccountTypeAndAccountNumber(accountType, sourceAccountNumber);

        String targetSortCode = transactionDetails.getTargetAccount().getAccountType();
        String targetAccountNumber = transactionDetails.getTargetAccount().getAccountNumber();
        Optional<Account> targetAccount = accountRepository
                .findByAccountTypeAndAccountNumber(targetSortCode, targetAccountNumber);

        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (isAmountAvailable(transactionDetails.getAmount(), sourceAccount.get().getCurrentBalance().getAmount())) {
                var transaction = new Transaction();

                transaction.setAmount(new LedgerAmount("ZAR",transactionDetails.getAmount()));
                transaction.setSourceAccountId(sourceAccount.get().getAccountId());
                transaction.setTargetAccountId(targetAccount.get().getAccountId());
                transaction.setTargetOwnerName(targetAccount.get().getCustomer());
                transaction.setInitiationDate(LocalDateTime.now());
                transaction.setCompletionDate(LocalDateTime.now());
                updateAccountBalance(sourceAccount.get(), transactionDetails.getAmount(), ACTION.WITHDRAW);
                updateAccountBalance(targetAccount.get(), transactionDetails.getAmount(), ACTION.DEPOSIT);
                transactionRepository.insert(transaction);
                return true;
            }
        }
        return false;
    }

    public void updateAccountBalance(Account account, double amount, ACTION action) {
        if (action == ACTION.WITHDRAW) {
            account.setCurrentBalance(new LedgerAmount("ZAR",account.getCurrentBalance().getAmount() - amount));
        } else if (action == ACTION.DEPOSIT) {
            account.setCurrentBalance(new LedgerAmount("ZAR",account.getCurrentBalance().getAmount() + amount));
        }
        accountRepository.save(account);
    }

    // TODO support OFFERING OVERDRAFTS
    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) > 0;
    }
}

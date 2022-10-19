package com.example.BankingAPI.service;


import com.example.BankingAPI.constants.ACTION;
import com.example.BankingAPI.model.Account;
import com.example.BankingAPI.model.LedgerAmount;
import com.example.BankingAPI.model.Transaction;
import com.example.BankingAPI.repository.AccountRepository;
import com.example.BankingAPI.repository.TransactionRepository;
import com.example.BankingAPI.utils.TransactionDetails;
import com.example.BankingAPI.utils.createTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static com.example.BankingAPI.utils.createTransaction.createTransferTransaction;

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
                //transactionRepository.insert(transaction);
                LedgerAmount ledgerAmount = new LedgerAmount("ZAR",transactionDetails.getAmount());
                createTransferTransaction(sourceAccount.get(),targetAccount.get(),ledgerAmount,transactionRepository, ACTION.WITHDRAW.toString());
                createTransferTransaction(targetAccount.get(),sourceAccount.get(),ledgerAmount,transactionRepository, ACTION.DEPOSIT.toString());
                updateAccountBalance(sourceAccount.get(), transactionDetails.getAmount(), ACTION.WITHDRAW);
                updateAccountBalance(targetAccount.get(), transactionDetails.getAmount(), ACTION.DEPOSIT);
                return true;
            }
        }
        return false;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }


    public void updateAccountBalance(Account account, double amount, ACTION action) {
        if (action == ACTION.WITHDRAW) {
            account.setCurrentBalance(new LedgerAmount("ZAR",account.getCurrentBalance().getAmount() - amount));
        } else if (action == ACTION.DEPOSIT) {
            account.setCurrentBalance(new LedgerAmount("ZAR",account.getCurrentBalance().getAmount() + amount));
        }
        accountRepository.save(account);
    }

    public Optional<List<Transaction>> transactionsFortheAccount(String accountNumber) {
        return transactionRepository.findAllBySourceAccountId(accountNumber);
    }

    // TODO support OFFERING OVERDRAFTS
    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) > 0;
    }
}

package com.example.BankingAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Data
@Document
public class Account {
   @Id
   private String accountId;

   private String accountType;

   private String accountNumber;

   private LedgerAmount currentBalance;

   private String bankName;

   private Customer customer;

   private List<Transaction> transactions;

   public Account(String accountType, String accountNumber, LedgerAmount currentBalance, String bankName, Customer customer) {
      this.accountType = accountType;
      this.accountNumber = accountNumber;
      this.currentBalance = currentBalance;
      this.bankName = bankName;
      this.customer = customer;
   }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LedgerAmount getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(LedgerAmount currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountId +
                ", accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentBalance=" + currentBalance +
                ", bankName='" + bankName + '\'' +
                ", customer=" + customer +
                ", transactions=" + transactions +
                '}';
    }

    @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (!(o instanceof Account))
         return false;
      Account account = (Account) o;
      return getAccountId() == account.getAccountId() && Objects.equals(account.getCurrentBalance(), getCurrentBalance())
            && Objects.equals(getAccountType(), account.getAccountType())
            && Objects.equals(getAccountNumber(), account.getAccountNumber())
            && Objects.equals(getBankName(), account.getBankName())
            && Objects.equals(getCustomer(), account.getCustomer())
            && Objects.equals(getTransactions(), account.getTransactions());
   }

   @Override
   public int hashCode() {
      return Objects.hash(
            getAccountId(),
            getAccountType(),
            getAccountNumber(),
            getCurrentBalance(),
            getBankName(),
            getCustomer(),
            getTransactions());
   }
}

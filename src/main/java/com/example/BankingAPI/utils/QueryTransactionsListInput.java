package com.example.BankingAPI.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class QueryTransactionsListInput {

    @NotBlank(message = "Account Number is mandatory")
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryTransactionsListInput)) return false;
        QueryTransactionsListInput that = (QueryTransactionsListInput) o;
        return Objects.equals(getAccountNumber(), that.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber());
    }

    @Override
    public String toString() {
        return "QueryTransactionsList{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}

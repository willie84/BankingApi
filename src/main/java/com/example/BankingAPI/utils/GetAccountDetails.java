package com.example.BankingAPI.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class GetAccountDetails {
    @NotBlank(message = "Account Number is mandatory")
    private String accountNumber;

    public GetAccountDetails() {
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAccountDetails)) return false;
        GetAccountDetails that = (GetAccountDetails) o;
        return Objects.equals(getAccountNumber(), that.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber());
    }

    @Override
    public String toString() {
        return "GetAccountDetails{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}

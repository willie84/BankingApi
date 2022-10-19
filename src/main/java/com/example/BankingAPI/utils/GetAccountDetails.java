package com.example.BankingAPI.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class GetAccountDetails {
    @NotBlank(message = "Account Number is mandatory")
    private String accountNumber;

    @NotBlank(message = "Account Type is mandatory")
    private String accountType;

    public GetAccountDetails() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAccountDetails)) return false;
        GetAccountDetails that = (GetAccountDetails) o;
        return Objects.equals(getAccountNumber(), that.getAccountNumber()) && Objects.equals(getAccountType(), that.getAccountType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getAccountType());
    }

    @Override
    public String toString() {
        return "GetAccountDetails{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}

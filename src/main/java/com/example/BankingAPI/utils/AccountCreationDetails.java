package com.example.BankingAPI.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AccountCreationDetails {
    @NotBlank(message = "Bank name is mandatory")
    private String bankName;

    @NotBlank(message = "idNumber is mandatory")
    private String IdNumber;

    @NotBlank(message = "accountType is mandatory")
    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public AccountCreationDetails() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String customerIdNumber) {
        this.IdNumber = customerIdNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountCreationDetails)) return false;
        AccountCreationDetails that = (AccountCreationDetails) o;
        return Objects.equals(getBankName(), that.getBankName()) && Objects.equals(getIdNumber(), that.getIdNumber()) && Objects.equals(getAccountType(), that.getAccountType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankName(), getIdNumber(), getAccountType());
    }

    @Override
    public String toString() {
        return "AccountCreationDetails{" +
                "bankName='" + bankName + '\'' +
                ", IdNumber='" + IdNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}

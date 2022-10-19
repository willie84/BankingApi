package com.example.BankingAPI.utils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class TransactionDetails {
    @NotBlank(message = "sourceAccount is mandatory")
    private GetAccountDetails sourceAccount;
    @NotBlank(message = "targetAccount is mandatory")
    private GetAccountDetails targetAccount;

    @Positive(message = "Transfer amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
    private double amount;

    private String reference;


    public TransactionDetails() {}

    public GetAccountDetails getSourceAccount() {
        return sourceAccount;
    }
    public void setSourceAccount(GetAccountDetails sourceAccount) {
        this.sourceAccount = sourceAccount;
    }
    public GetAccountDetails getTargetAccount() {
        return targetAccount;
    }
    public void setTargetAccount(GetAccountDetails targetAccount) {
        this.targetAccount = targetAccount;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }


    @Override
    public String toString() {
        return "TransactionInput{" +
                "sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                ", amount=" + amount +
                ", reference='" + reference + '\'' +
                '}';
    }
}

package com.example.BankingAPI.model;

import java.util.Objects;

public class LedgerAmount {
    private String currency;
    private double amount;

    public LedgerAmount(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedgerAmount)) return false;
        LedgerAmount that = (LedgerAmount) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "LedgerAmount{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}

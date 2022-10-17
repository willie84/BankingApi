package com.example.BankingAPI.utils;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class AccountCreationDetails {
   @NotBlank(message = "Bank name is mandatory")
   private String bankName;

   @NotBlank(message = "customerId is mandatory")
   private String IdNumber;

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
      if (this == o)
         return true;
      if (!(o instanceof AccountCreationDetails))
         return false;
      AccountCreationDetails that = (AccountCreationDetails) o;
      return Objects.equals(getBankName(), that.getBankName()) && Objects.equals(getIdNumber(), that.getIdNumber());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getBankName(), getIdNumber());
   }

   @Override
   public String toString() {
      return "AccountCreationDetails{" + "bankName='" + bankName + '\'' + ", IdNumber='" + IdNumber + '\'' + '}';
   }
}

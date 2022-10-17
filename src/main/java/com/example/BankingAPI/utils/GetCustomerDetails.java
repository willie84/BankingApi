package com.example.BankingAPI.utils;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class GetCustomerDetails {
   @NotBlank(message = "Customer idNumber is mandatory")
   private String idNumber;

   public GetCustomerDetails() {
   }

   public String getidNumber() {
      return idNumber;
   }

   public void setidNumber(String idNumber) {
      this.idNumber = idNumber;
   }

   @Override
   public String toString() {
      return "GetCustomerDetails{" + "idNumber='" + idNumber + '\'' + '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (!(o instanceof GetCustomerDetails))
         return false;
      GetCustomerDetails that = (GetCustomerDetails) o;
      return Objects.equals(getidNumber(), that.getidNumber());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getidNumber());
   }
}

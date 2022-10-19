package com.example.BankingAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document
public class Customer {
   @Id
   private String customerId;

   private String name;

   private String address;

   private String idNumber;

   private String cellphone;

   private String password;

   public Customer(String name, String address, String idNumber) {
      this.name = name;
      this.address = address;
      this.idNumber = idNumber;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getIdNumber() {
      return idNumber;
   }

   public void setIdNumber(String IdNumber) {
      this.idNumber = IdNumber;
   }

   public String getCellphone() {
      return cellphone;
   }

   public void setCellphone(String cellphone) {
      this.cellphone = cellphone;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (!(o instanceof Customer))
         return false;
      Customer customer = (Customer) o;
      return Objects.equals(getName(), customer.getName()) && Objects.equals(getAddress(), customer.getAddress())
            && Objects.equals(getIdNumber(), customer.getIdNumber())
            && Objects.equals(getCellphone(), customer.getCellphone())
            && Objects.equals(getPassword(), customer.getPassword());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getName(), getAddress(), getIdNumber(), getCellphone(), getPassword());
   }

   @Override
   public String toString() {
      return "Customer{" + "name='" + name + '\'' + ", address='" + address + '\'' + ", idNumber='" + idNumber + '\''
            + ", cellphone='" + cellphone + '\'' + ", password='" + password + '\'' + '}';
   }
}

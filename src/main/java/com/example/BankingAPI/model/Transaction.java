package com.example.BankingAPI.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Transaction {
   @Id
   private String transactionId;
   private String sourceAccountId;

   private String targetAccountId;

   private Customer targetOwnerName;

   private LedgerAmount transactionAmount;

   private LocalDateTime initiationDate;

   private LocalDateTime completionDate;

   public Transaction() {
   }

   public String getTransactionID() {
      return transactionId;
   }

   public void setTransactionID(String transactionID) {
      this.transactionId = transactionID;
   }

   public String getSourceAccountId() {
      return sourceAccountId;
   }

   public void setSourceAccountId(String sourceAccountId) {
      this.sourceAccountId = sourceAccountId;
   }

   public String getTargetAccountId() {
      return targetAccountId;
   }

   public void setTargetAccountId(String targetAccountId) {
      this.targetAccountId = targetAccountId;
   }

   public Customer getTargetOwnerName() {
      return targetOwnerName;
   }

   public void setTargetOwnerName(Customer targetOwnerName) {
      this.targetOwnerName = targetOwnerName;
   }

   public LedgerAmount getTransactionAmount() {
      return transactionAmount;
   }

   public void setTransactionAmount(LedgerAmount transactionAmount) {
      this.transactionAmount = transactionAmount;
   }

   public LocalDateTime getInitiationDate() {
      return initiationDate;
   }

   public void setInitiationDate(LocalDateTime initiationDate) {
      this.initiationDate = initiationDate;
   }

   public LocalDateTime getCompletionDate() {
      return completionDate;
   }

   public void setCompletionDate(LocalDateTime completionDate) {
      this.completionDate = completionDate;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (!(o instanceof Transaction))
         return false;
      Transaction that = (Transaction) o;
      return Objects.equals(getTransactionID(), that.getTransactionID())
            && Objects.equals(getSourceAccountId(), that.getSourceAccountId())
            && Objects.equals(getTargetAccountId(), that.getTargetAccountId())
            && Objects.equals(getTargetOwnerName(), that.getTargetOwnerName())
            && Objects.equals(getTransactionAmount(), that.getTransactionAmount())
            && Objects.equals(getInitiationDate(), that.getInitiationDate())
            && Objects.equals(getCompletionDate(), that.getCompletionDate());
   }

   @Override
   public int hashCode() {
      return Objects.hash(
            getTransactionID(),
            getSourceAccountId(),
            getTargetAccountId(),
            getTargetOwnerName(),
            getTransactionAmount(),
            getInitiationDate(),
            getCompletionDate());
   }

   @Override
   public String toString() {
      return "Transaction{" + "transactionID='" + transactionId + '\'' + ", sourceAccountId='" + sourceAccountId + '\''
            + ", targetAccountId='" + targetAccountId + '\'' + ", targetOwnerName=" + targetOwnerName
            + ", transactionAmount=" + transactionAmount + ", initiationDate=" + initiationDate + ", completionDate="
            + completionDate + '}';
   }
}

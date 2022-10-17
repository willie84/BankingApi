package com.example.BankingAPI.validators;

import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.utils.AccountCreationDetails;
import com.example.BankingAPI.utils.CustomerCreationDetails;
import com.example.BankingAPI.utils.GetAccountDetails;
import com.example.BankingAPI.utils.GetCustomerDetails;

public class InputValidator {
   public static boolean isSearchCriteriaValid(GetAccountDetails getAccountDetails) {
      return constants.ACCOUNT_NUMBER_PATTERN.matcher(getAccountDetails.getAccountNumber()).find()
            && !getAccountDetails.getAccountNumber().isBlank();
   }

   public static boolean isCustomerSearchCriteriaValid(GetCustomerDetails getCustomerDetails) {
      return !getCustomerDetails.getidNumber().isBlank()
            && getCustomerDetails.getidNumber().length() == constants.ID_LENGTH;
   }

   public static boolean isAccountNoValid(String accountNo) {
      return constants.ACCOUNT_NUMBER_PATTERN.matcher(accountNo).find();
   }

   public static boolean isCreateAccountCriteriaValid(AccountCreationDetails accountCreationDetails) {
      return (!accountCreationDetails.getBankName().isBlank() && !accountCreationDetails.getIdNumber().isBlank());
   }

   public static boolean isCreateCustomerCriteriaValid(CustomerCreationDetails customerCreationDetails) {
      return (!customerCreationDetails.getName().isBlank()
            && !customerCreationDetails.getAddress().isBlank()
            && !customerCreationDetails.getIdNumber().isBlank());
   }

//   public static boolean isSearchTransactionValid(TransactionInput transactionInput) {
//      // TODO Add checks for large amounts; consider past history of account holder and location of transfers
//
//      if (!isSearchCriteriaValid(transactionInput.getSourceAccount()))
//         return false;
//
//      if (!isSearchCriteriaValid(transactionInput.getTargetAccount()))
//         return false;
//
//      if (transactionInput.getSourceAccount().equals(transactionInput.getTargetAccount()))
//         return false;
//
//      return true;
//   }
}

package com.example.BankingAPI.validators;

import com.example.BankingAPI.constants.ACCOUNT_TYPE;
import com.example.BankingAPI.constants.constants;
import com.example.BankingAPI.utils.AccountCreationDetails;
import com.example.BankingAPI.utils.CustomerCreationDetails;
import com.example.BankingAPI.utils.GetAccountDetails;
import com.example.BankingAPI.utils.GetCustomerDetails;
import com.example.BankingAPI.utils.QueryTransactionsListInput;
import com.example.BankingAPI.utils.TransactionDetails;

public class InputValidator {
    public static boolean isSearchCriteriaValid(GetAccountDetails getAccountDetails) {
        return constants.ACCOUNT_NUMBER_PATTERN.matcher(getAccountDetails.getAccountNumber()).find()
                && !getAccountDetails.getAccountNumber().isBlank();
    }

    public static boolean isCustomerSearchCriteriaValid(GetCustomerDetails getCustomerDetails) {
        return !getCustomerDetails.getidNumber().isBlank()
                && getCustomerDetails.getidNumber().length() == constants.ID_LENGTH;
    }

    public static boolean isCustomerIDRightlength(String customerID) {
        return customerID.length() == constants.ID_LENGTH;
    }

    public static boolean isAccountNoValid(String accountNo) {
        return constants.ACCOUNT_NUMBER_PATTERN.matcher(accountNo).find();
    }

    public static boolean isAccountTypeCorrect(String accountType) {
        if (ACCOUNT_TYPE.contains(accountType)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCreateAccountCriteriaValid(AccountCreationDetails accountCreationDetails) {
        return (!accountCreationDetails.getBankName().isBlank() && !accountCreationDetails.getIdNumber().isBlank() && !accountCreationDetails.getAccountType().isBlank() && isAccountTypeCorrect(accountCreationDetails.getAccountType()));
    }

    public static boolean isCreateCustomerCriteriaValid(CustomerCreationDetails customerCreationDetails) {
        return (!customerCreationDetails.getName().isBlank()
                && !customerCreationDetails.getAddress().isBlank()
                && !customerCreationDetails.getIdNumber().isBlank() && isCustomerIDRightlength(customerCreationDetails.getIdNumber()));
    }

    public static boolean isSearchTransactionValid(TransactionDetails transactionDetails) {
        if (!isSearchCriteriaValid(transactionDetails.getSourceAccount()))
            return false;

        if (!isSearchCriteriaValid(transactionDetails.getTargetAccount()))
            return false;

        if (transactionDetails.getSourceAccount().equals(transactionDetails.getTargetAccount()))
            return false;

        return true;

    }

    public static boolean isSearchTransactionQueryListValid(QueryTransactionsListInput queryTransactionsListInput) {
        return (!queryTransactionsListInput.getAccountNumber().isBlank());
    }
}

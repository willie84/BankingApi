package com.example.BankingAPI.constants;

import java.util.regex.Pattern;

public class constants {

    public static final String SUCCESS =
            "Transaction completed successfully";
    public static final String NO_ACCOUNT_FOUND =
            "Unable to find an account matching this accountType and account number";
    public static final String INVALID_SEARCH_CRITERIA =
            "The provided account number did not match the expected format";

    public static final String INSUFFICIENT_ACCOUNT_BALANCE =
            "Your account does not have sufficient balance";

    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{13}";
    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{13}$");
    public static final int ID_LENGTH = 13;

    public static final String INVALID_TRANSACTION =
            "Account information is invalid or transaction has been denied for your protection. Please try again.";
    public static final String INVALID_ACCOUNT_TYPE =
            "Account Type is invalid. Kindly use either of these CURRENT,\n" +
                    "    SAVINGS,\n" +
                    "    CHEQUE,\n" +
                    "    LOAN; Please try again.";
    public static final String DUPLICATE_ACCOUNT_TYPE =
            "You can only have one account of the one type in one bank. Choose another account type from CURRENT, +\n" +
                    "                    \"    SAVINGS,\\n\" +\n" +
                    "                    \"    CHEQUE,\\n\" +\n" +
                    "                    \"    LOAN;  or open account in another Bank";
    public static final String CREATE_ACCOUNT_FAILED =
            "Error happened during creating new account";

    public static final String CREATE_CUSTOMER_FAILED =
            "Error happened during creating new customer";
    public static final Object INVALID_ID_LENGTH = "The ID Length should be 13 in length";
    public static final Object CUSTOMER_ALREADY_EXISTS_WITH_SAME_ID_NUMBER = "Customer with same ID Number already exists" ;
    public static final String ACCOUNT_HAS_BEEN_FOUND = "Account has been found";
    public static final Object NO_CUSTOMER_FOUND ="No Customer with such ID Number was found in the database" ;
    public static final String DEPOSIT_WAS_MADE_TO_THIS_ACCOUNT = "A deposit was made to this account";
    public static final String WITHDRAWAL_WAS_MADE_TO_THIS_ACCOUNT ="A withdrawal was made in this account" ;
}

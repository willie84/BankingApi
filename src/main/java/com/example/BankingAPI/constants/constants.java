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

    public static final String SORT_CODE_PATTERN_STRING = "[0-9]{2}-[0-9]{2}-[0-9]{2}";

    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{13}";
    public static final Pattern SORT_CODE_PATTERN = Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}$");
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
}

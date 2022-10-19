package com.example.BankingAPI.constants;

import java.util.HashSet;
import java.util.Set;

public enum ACCOUNT_TYPE {
    CURRENT,
    SAVINGS,
    CHEQUE,
    LOAN;

    private final static Set<String> values = new HashSet<String>(ACCOUNT_TYPE.values().length);

    static{
        for(ACCOUNT_TYPE f: ACCOUNT_TYPE.values())
            values.add(f.name());
    }

    public static boolean contains( String value ){
        return values.contains(value);
    }
}

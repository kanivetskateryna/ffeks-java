package com.dnu.ffeks.lab2.passwordvalidation;

import static com.dnu.ffeks.lab1.UtilityClass.checkEmptinessOfRow;

public class PasswordComplexityCheckerImpl implements PasswordComplexityChecker {
    /*
    ^				        matches the beginning of the line
    (?=(.*\d)) 		        a look-ahead expression, requires at least one digit to be present
    .*				        matches n characters, where n >= 0
    (?=.*[A-Z])(?=.*[a-z])  a capturing group of look-ahead expressions, requires at least one uppercase letter and
                            one lowercase letter to be present
    [\da-zA-Z_]	            match any numbers or letters or underline symbol
    {8,}				    matches at least 8 symbols
    $				        matches the end of the line
    */
    private static final String PASSWORD_EXP = "^(?=(.*\\d))(?=.*[A-Z])(?=.*[a-z])[\\da-zA-Z_]{8,}";
    private final String password;

    public PasswordComplexityCheckerImpl(String password) {
        this.password = password;
    }

    @Override
    public boolean isStrong() {
        return password.matches(PASSWORD_EXP);
    }

    @Override
    public void checkEmptiness() {
        checkEmptinessOfRow(password);
    }
}

package com.dnu.ffeks.lab2.passwordvalidation;

@lombok.experimental.UtilityClass
public final class UtilityClass {

    public static void checkEmptinessOfRow(String row) {
        if (row.isBlank()) {
            throw new EmptyRowException("Your row is empty. Please check the character input.");
        }
    }

    public static void checkPasswordExistence(String password) {
        if (password.isEmpty()) {
            throw new com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException();
        }
    }
}

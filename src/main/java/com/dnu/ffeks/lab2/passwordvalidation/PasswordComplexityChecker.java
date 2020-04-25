package com.dnu.ffeks.lab2.passwordvalidation;

public interface PasswordComplexityChecker {

    boolean isStrong();

    void checkEmptiness();
}

package com.dnu.ffeks.lab2.passwordvalidation;

public class PasswordValidatorImpl implements PasswordValidator {

    public static final String WEAK_PASSWORD_MESSAGE = "Your password is weak!";
    private final PasswordComplexityChecker checker;

    public PasswordValidatorImpl(PasswordComplexityChecker checker) {
        this.checker = checker;
    }

    @Override
    public void validate() {
        boolean isWeakPassword = !checker.isStrong();
        if (isWeakPassword) {
//            System.out.println(WEAK_PASSWORD_MESSAGE);
            throw new WeakPasswordException();
        }
    }
}

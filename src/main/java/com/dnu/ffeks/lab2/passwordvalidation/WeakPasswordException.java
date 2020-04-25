package com.dnu.ffeks.lab2.passwordvalidation;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException() {
    }

    public WeakPasswordException(String message) {
        super(message);
    }
}

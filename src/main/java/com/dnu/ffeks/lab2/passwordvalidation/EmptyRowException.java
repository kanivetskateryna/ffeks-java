package com.dnu.ffeks.lab2.passwordvalidation;

public class EmptyRowException extends RuntimeException {

    public EmptyRowException() {
    }

    public EmptyRowException(String message) {
        super(message);
    }
}
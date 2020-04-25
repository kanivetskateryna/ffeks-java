package com.dnu.ffeks.lab2.passwordvalidation;

import java.io.BufferedReader;
import java.io.IOException;

public class PasswordReaderImpl implements PasswordReader {

    @Override
    public String getPasswordFromReader(BufferedReader reader) throws IOException {
        return reader.readLine();
    }
}
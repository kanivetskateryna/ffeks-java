package com.dnu.ffeks.lab2.passwordvalidation;

import java.io.BufferedReader;
import java.io.IOException;

public interface PasswordReader {

    String getPasswordFromReader(BufferedReader reader) throws IOException;
}

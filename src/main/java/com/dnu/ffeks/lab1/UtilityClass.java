package com.dnu.ffeks.lab1;

import com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException;

import java.util.List;

@lombok.experimental.UtilityClass
public final class UtilityClass {

    public static final String EMPTY_ROW_MESSAGE = "Your row is empty. Please check the character input.";

    public static void checkEmptinessOfList(List<String> rows) {
        if (rows.isEmpty()) {
            throw new EmptyListException("Nothing to compare. The list is empty.");
        }
    }

    public static void checkEmptinessOfRow(String row) {
        if (row.isBlank()) {
            System.out.println(EMPTY_ROW_MESSAGE);
            throw new EmptyRowException();
        }
    }
}

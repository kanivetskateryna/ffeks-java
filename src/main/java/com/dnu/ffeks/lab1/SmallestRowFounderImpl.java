package com.dnu.ffeks.lab1;

import com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmallestRowFounderImpl implements SmallestRowFounder {

    public static final String INVALID_CHARACTER_INPUT = "Please check the character input.";
    private final List<String> rows;

    public SmallestRowFounderImpl() {
        rows = new ArrayList<>();
    }

    @Override
    public String getTheSmallestRow(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String row = reader.readLine();
            try {
                UtilityClass.checkEmptinessOfRow(row);
            } catch (EmptyRowException exc) {
                System.out.println(INVALID_CHARACTER_INPUT);
                System.out.println("Try again:");
                return getTheSmallestRow(in);
            }

            rows.add(row);
            fillListOfRows(reader);

        } catch (IOException exc) {
            throw new InvalidRowException(INVALID_CHARACTER_INPUT);
        }

        return getTheSmallestRowOfList(rows);
    }

    private void fillListOfRows(BufferedReader reader) throws IOException {
        String row;
        while (true) {
            try {
                row = reader.readLine();
            } catch (IOException exc) {
                System.out.println(INVALID_CHARACTER_INPUT);
                throw exc;
            }
            if (row.isEmpty()) {
                break;
            }
            rows.add(row);
        }
    }

    @Override
    public String getTheSmallestRowOfList(List<String> rows) {
        UtilityClass.checkEmptinessOfList(rows);
        rows.sort(Comparator.comparingInt(String::length));
        return rows.get(0);
    }

}

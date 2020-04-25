package com.dnu.ffeks.multithreading;

import com.dnu.ffeks.lab1.UtilityClass;
import com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Увести n рядків з консолі, знайти найкоротший рядок. Вивести цей рядок і його довжину.
 */
public class Lab1 {

    public static final String INVALID_CHARACTER_INPUT = "Please check the character input.";

    public static void main(String[] args) throws IOException {

        Runner runner = new Runner();

        String theSmallestRow = runner.getTheSmallestRow();

        System.out.println("The smallest row: " + theSmallestRow);
        System.out.println("It's length: " + theSmallestRow.length());
    }

    public static class Runner {

        private final List<String> rows;

        public Runner() {
            this.rows = new ArrayList<>();
        }

        public String getTheSmallestRow() throws IOException {
            System.out.println("Enter rows:");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String row = reader.readLine();
            try {
                UtilityClass.checkEmptinessOfRow(row);
            } catch (EmptyRowException exc) {
                System.err.println(INVALID_CHARACTER_INPUT);
                System.err.println("Try again:");
                return getTheSmallestRow();
            }

            rows.add(row);
            fillListOfRows();

            return getTheSmallestRowOfList(rows);
        }

        private void fillListOfRows() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String row;
            while (true) {
                try {
                    row = reader.readLine();
                } catch (IOException exc) {
                    System.err.println(INVALID_CHARACTER_INPUT);
                    throw exc;
                }
                if (row.isEmpty()) {
                    break;
                }
                rows.add(row);
            }
        }
    }

    public static String getTheSmallestRowOfList(List<String> rows) {
        UtilityClass.checkEmptinessOfList(rows);
        rows.sort(Comparator.comparingInt(String::length));
        return rows.get(0);
    }

    public static void multithreadingForLab1() throws IOException {

        Runner runner = new Runner();

        String theSmallestRow = runner.getTheSmallestRow();

        System.out.println("The smallest row: " + theSmallestRow);
        System.out.println("It's length: " + theSmallestRow.length());
    }
}

package com.dnu.ffeks.utils;

//import com.dnu.ffeks.lab1.Task6;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class UtilClass {

    @SneakyThrows(IOException.class)
    public static String getContentFromFile(String fileName) {
        File file = getFileFromResources(fileName);
        return new String(Files.readAllBytes(file.toPath()));
    }

    @SneakyThrows(IOException.class)
    public static void writeContentToFile(String fileName, String str) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.append(str);
            writer.newLine();
        }
    }

    @SneakyThrows(IOException.class)
    public static String[] fillArray(BufferedReader reader) {
        System.out.println("Enter count of rows: ");
        int countOfRows = Integer.parseInt(reader.readLine());
        String[] rows = new String[countOfRows];

        for (int i = 0; i < countOfRows; i++) {
            String row = reader.readLine();
            if (row.isEmpty()) {
                break;
            }
            rows[i] = row;
        }
        return rows;
    }

    @SneakyThrows(IOException.class)
    public static List<String> fillList(BufferedReader reader) throws IOException {
        List<String> rows = new ArrayList<>();

        while (true) {
            String row = reader.readLine();
            if (row.isEmpty()) {
                break;
            }
            rows.add(row);
        }
        return rows;
    }

    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = UtilityClass.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    }
}

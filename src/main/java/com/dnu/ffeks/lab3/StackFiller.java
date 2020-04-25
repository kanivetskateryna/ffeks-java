package com.dnu.ffeks.lab3;

import com.dnu.ffeks.utils.UtilClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public abstract class StackFiller {

    private Stack<String> stack;

    public StackFiller(Stack<String> stack) {
        this.stack = stack;
    }

    public abstract void setStack(Stack<String> stringStack);

    public abstract void fillStackOfGeneratedValues();

    public void fillStackFromFile(String fileName) throws IOException {
        File file = Task31.getFileFromResources(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                stack.push(line);
            }
        }
    }
}

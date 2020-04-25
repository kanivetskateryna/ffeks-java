package com.dnu.ffeks.lab3;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class Task31 {

    public static void main() throws IOException {
        Stack<String> stack = new Stack<>();

        StackFillerImpl stackFiller = new StackFillerImpl(stack);
        stackFiller.fillStackFromFile("working_with_stack.txt");

        System.out.println("-----------Before reversing-------------");
        outputStack(stack);

        Task31.reverse(stack);

        System.out.println("-----------After reversing-------------");
        outputStack(stack);
    }

    private static void outputStack(Stack<String> stack) {
        stack.stream().map(s -> s + " ").forEach(System.out::print);
        System.out.println();
    }

    public static void reverse(Stack<String> stack) {
        if (!stack.isEmpty()) {
            String val = stack.peek();
            stack.pop();
            reverse(stack);
            insertAtBottom(stack, val);
        }
    }

    private static void insertAtBottom(Stack<String> stack, String val) {
        if (stack.isEmpty()) {
            stack.push(val);
        } else {
            String nextVal = stack.peek();
            stack.pop();
            insertAtBottom(stack, val);
            stack.push(nextVal);
        }
    }

    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = Task31.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    }
}

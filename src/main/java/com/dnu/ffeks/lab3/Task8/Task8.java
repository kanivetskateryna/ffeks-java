package com.dnu.ffeks.lab3.Task8;

import com.dnu.ffeks.lab3.StackFillerImpl;

import java.util.Stack;

/**
 * Задати два стека, поміняти інформацію місцями.
 */
public class Task8 {

    private static final int WORD_LENGTH = 5;
    private static final int COUNT = 5;

    public static void main() {
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();

        fillStacks(stack1, stack2);

        System.out.println("-----------Before swapping-----------");
        outputStacks(stack1, stack2);

        StackSwapper stackSwapper = new StackSwapperImpl<>(stack1, stack2);
        stackSwapper.swapStacks();

        System.out.println("-----------After swapping-----------");
        outputStacks(stack1, stack2);
    }

    private static void fillStacks(Stack<String> stack1, Stack<String> stack2) {
        StackFillerImpl stackFiller = new StackFillerImpl(stack1, COUNT, WORD_LENGTH);
        stackFiller.fillStackOfGeneratedValues();
        stackFiller.setStack(stack2);
        stackFiller.fillStackOfGeneratedValues();
    }

    private static void outputStacks(Stack<String> stack1, Stack<String> stack2) {
        stack1.stream().map(s -> s + " ").forEach(System.out::print);
        System.out.println();
        stack2.stream().map(s -> s + " ").forEach(System.out::print);
        System.out.println();
    }
}

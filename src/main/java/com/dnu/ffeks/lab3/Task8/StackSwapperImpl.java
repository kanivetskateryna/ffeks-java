package com.dnu.ffeks.lab3.Task8;

import java.util.Stack;

public class StackSwapperImpl<T> implements StackSwapper {

    private Stack<T> stack1;
    private Stack<T> stack2;

    public StackSwapperImpl(Stack<T> stack1, Stack<T> stack2) {
        this.stack1 = stack1;
        this.stack2 = stack2;
    }

    /**
     * This method uses recursion for swapping elements of stacks
     */
    @Override
    public void swapStacks() {
        if (stack1.isEmpty() || stack2.isEmpty()) {
            return;
        }

        T stackVal1 = stack1.pop();
        T stackVal2 = stack2.pop();

        swapStacks();

        stack1.push(stackVal2);
        stack2.push(stackVal1);
    }
}

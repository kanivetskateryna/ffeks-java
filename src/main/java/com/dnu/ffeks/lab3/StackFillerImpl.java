package com.dnu.ffeks.lab3;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Stack;

public class StackFillerImpl extends StackFiller {

    private Stack<String> stringStack;
    private int countOfElements;
    private int wordLength;

    public StackFillerImpl(Stack<String> stringStack, int countOfElements, int wordLength) {
        super(stringStack);
        this.stringStack = stringStack;
        this.countOfElements = countOfElements;
        this.wordLength = wordLength;
    }

    public StackFillerImpl(Stack<String> stringStack) {
        super(stringStack);
    }

    @Override
    public void setStack(Stack<String> stringStack) {
        this.stringStack = stringStack;
    }

    @Override
    public void fillStackOfGeneratedValues() {
        for (int i = 0; i < countOfElements; i++) {
            stringStack.push(RandomStringUtils.randomAlphabetic(wordLength));
        }
    }
}

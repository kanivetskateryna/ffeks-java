package com.dnu.ffeks.multithreading.exchanger;

import com.dnu.ffeks.multithreading.Lab1;

import java.io.IOException;
import java.util.concurrent.Exchanger;

public class Exchangers {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Task1("Lab 1", exchanger);
        new Task2("Lab 2", exchanger);
    }
}

class Task1 implements Runnable {

    String threadName;
    String str;

    Exchanger<String> ex;

    Task1(String n, Exchanger<String> exchanger) {
        threadName = n;
        ex = exchanger;
        str = "";
        new Thread(this, threadName).start();
    }

    @Override
    public void run() {

        try {
            Lab1.Runner runner = new Lab1.Runner();
            String result = runner.getTheSmallestRow();
            str = ex.exchange(result);
        } catch (InterruptedException | IOException exc) {
            System.out.println(exc);
        }
        System.out.println("-------------------");
    }
}

class Task2 implements Runnable {

    String threadName;
    String str;

    Exchanger<String> ex;

    Task2(String threadName, Exchanger<String> exchanger) {
        this.threadName = threadName;
        ex = exchanger;
        str = "";
        new Thread(this, this.threadName).start();
    }

    @Override
    public void run() {

        try {
            str = ex.exchange("");
            ExecutionTask2.Runner validator = new ExecutionTask2.Runner();
            validator.isPasswordStrong(str);

        } catch (InterruptedException | IOException exc) {
            System.out.println(exc);
        }
        System.out.println();
        System.out.println("-------------------------");
    }
}

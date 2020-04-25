package com.dnu.ffeks.multithreading.simplesynchronization;

import com.dnu.ffeks.multithreading.Lab1;
import com.dnu.ffeks.multithreading.Lab2;

import java.io.IOException;

public class Runners {

    private int flag;

    public synchronized void lab1() throws IOException {
        while (flag != 0) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                System.out.printf("Thread %s was interrupted.%n", Thread.currentThread().getName());
            }
        }
        this.flag = 1;
        Lab1.multithreadingForLab1();
        System.out.println("Lab 1 finished.");
        System.out.println("================");
        notify();
    }

    public synchronized void lab2() throws IOException {
        while (flag != 1) {
            try {
                wait(2000);
            } catch (InterruptedException e) {
                System.out.printf("Thread %s was interrupted.%n", Thread.currentThread().getName());
            }
        }
        this.flag = 2;
        Lab2.forMultithreading();
        System.out.println();
        System.out.println("Lab 2 finished.");
        System.out.println("================");
        notify();
    }

    public synchronized void lab3() {
        while (flag != 2) {
            try {
                wait(2000);
            } catch (InterruptedException e) {
                System.out.printf("Thread %s was interrupted.%n", Thread.currentThread().getName());
            }
        }
        this.flag = 0;
        com.dnu.ffeks.lab3.Task8.Task8.main();
        System.out.println();
        System.out.println("Lab 3 finished.");
        System.out.println("================");
        notify();
    }
}

class Thread1 implements Runnable {

    private Thread thread;
    private Runners runners;

    public Thread1(Runners runners) {
        this.runners = runners;
        thread = new Thread(this, "Lab 1");
        thread.start();
    }

    @Override
    public void run() {
        try {
            runners.lab1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }
}

class Thread2 implements Runnable {

    private Thread thread;
    private Runners runners;

    public Thread2(Runners runners) {
        this.runners = runners;
        thread = new Thread(this, "Lab 2");
        thread.start();
    }

    @Override
    public void run() {
        try {
            runners.lab2();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }
}

class Thread3 implements Runnable {

    private Thread thread;
    private Runners runners;

    public Thread3(Runners runners) {
        this.runners = runners;
        thread = new Thread(this, "Lab 3");
        thread.start();
    }

    @Override
    public void run() {
        runners.lab3();
    }

    public Thread getThread() {
        return thread;
    }

}

class MainRunner {

    public static void main(String[] args) {
        Runners runners = new Runners();

        Thread1 thread1 = new Thread1(runners);
        Thread2 thread2 = new Thread2(runners);
        Thread3 thread3 = new Thread3(runners);

        try {
            thread1.getThread().join();
            thread2.getThread().join();
            thread3.getThread().join();
        } catch (InterruptedException exc) {
            System.out.println("Threads were interrupted.");
        }

        System.out.println("All labs were executed.");
        System.out.printf("Finishing %s thread...", Thread.currentThread().getName());
    }
}
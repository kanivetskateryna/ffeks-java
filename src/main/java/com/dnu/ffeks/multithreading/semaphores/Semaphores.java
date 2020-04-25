package com.dnu.ffeks.multithreading.semaphores;

import com.dnu.ffeks.lab3.Task8.Task8;
import com.dnu.ffeks.multithreading.Lab1;
import com.dnu.ffeks.multithreading.Lab2;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Semaphores {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        new Thread1(semaphore,"Lab 1");
        new Thread2(semaphore,"Lab 2");
        new Thread3(semaphore,"Lab 3");
    }
}

class Thread1 implements Runnable {

    Semaphore semaphore;
    String threadName;

    Thread1(Semaphore s, String n) {
        semaphore = s;
        threadName = n;
        new Thread(this, threadName).start();
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();
            System.out.println(threadName);
            Lab1.multithreadingForLab1();
        } catch (InterruptedException | IOException exc) {
            System.out.println(exc);
        }
        System.out.println("----------------------");
        semaphore.release();
    }
}

class Thread2 implements Runnable {

    Semaphore semaphore;
    String threadName;

    Thread2(Semaphore s, String n) {
        semaphore = s;
        threadName = n;
        new Thread(this, threadName).start();
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();
            System.out.println(threadName);
            Lab2.forMultithreading();
        } catch (InterruptedException | IOException exc) {
            System.out.println(exc);
        }
        System.out.println("----------------------");
        semaphore.release();
    }
}

class Thread3 implements Runnable {

    Semaphore semaphore;
    String threadName;

    Thread3(Semaphore s, String n) {
        semaphore = s;
        threadName = n;
        new Thread(this, threadName).start();
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();
            System.out.println(threadName);
            Task8.main();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        System.out.println("----------------------");
        semaphore.release();
    }
}
package com.dnu.ffeks.multithreading.countdown;

import com.dnu.ffeks.lab3.Task31;
import com.dnu.ffeks.lab3.Task8.Task8;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread app1 = new Thread(new Application1("App-1", countDownLatch));
        Thread app2 = new Thread(new Application2("App-2", countDownLatch));

        // initialize applications
        app1.start();
        app2.start();

        try {
            //wait until countDownLatch reduces to 0.
            countDownLatch.await();
            //As all applications are up, print the message
            System.out.println("All applications are up and running.");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}

class Application1 implements Runnable {
    private String name;
    private CountDownLatch countDownLatch;

    public Application1(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            System.out.println(name + " started. ");
            Task8.main();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(name + " is Up and running.");
        //reduce the count by 1
        countDownLatch.countDown();
    }
}

class Application2 implements Runnable {
    private String name;
    private CountDownLatch countDownLatch;

    public Application2(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            System.out.println(name + " started. ");
            Thread.sleep(4000);
            Task31.main();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(name + " is Up and running.");
        //reduce the count by 1
        countDownLatch.countDown();
    }
}



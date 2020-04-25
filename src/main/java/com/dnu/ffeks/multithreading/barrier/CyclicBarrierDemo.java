package com.dnu.ffeks.multithreading.barrier;

import com.dnu.ffeks.lab3.Task8.Task8;
import com.dnu.ffeks.lab4.task7.Task7;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo implements Runnable {
    public static CyclicBarrier newBarrier = new CyclicBarrier(2, new ReachedBarrierAction());

    public static void main(String[] args) {
        // parent thread
        CyclicBarrierDemo test = new CyclicBarrierDemo();

        Thread t1 = new Thread(test);
        t1.start();
    }

    @Override
    public void run() {
        // objects on which the child thread has to run
        BarLab1 lab1 = new BarLab1();
        BarLab2 lab2 = new BarLab2();

        // creation of child thread
        Thread t1 = new Thread(lab1);
        Thread t2 = new Thread(lab2);

        // moving child thread to runnable state
        t1.start();
        t2.start();

        try {
            CyclicBarrierDemo.newBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // Resetting the newBarrier
        newBarrier.reset();
        System.out.println("Barrier reset successful");
    }
}

class BarLab1 implements Runnable {
    @Override
    public void run() {
//        Task8.main();
        Task7.main();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CyclicBarrierDemo.newBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class BarLab2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Is the barrier broken? - " + CyclicBarrierDemo.newBarrier.isBroken());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Task7.main();
        Task8.main();
        try {
            CyclicBarrierDemo.newBarrier.await();
            // number of parties waiting at the barrier
            System.out.println("Number of parties waiting at the barrier " +
                    "at this point = " + CyclicBarrierDemo.newBarrier.getNumberWaiting());
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class ReachedBarrierAction implements Runnable {
    @Override
    public void run() {
        System.out.println("Barrier was reached!");
    }
}

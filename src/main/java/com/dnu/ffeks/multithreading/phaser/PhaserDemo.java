package com.dnu.ffeks.multithreading.phaser;

import com.dnu.ffeks.lab3.Task31;
import com.dnu.ffeks.lab3.Task8.Task8;

import java.io.IOException;
import java.util.concurrent.Phaser;

public class PhaserDemo {

    private static final Phaser phaser = new Phaser();

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        new Phas1(phaser, "Lab-3");
        new Phas2(phaser, "Lab-4");
    }
}

class Phas1 implements Runnable {

    Phaser phaser;

    public Phas1(Phaser phaser, String s) {
        this.phaser = phaser;
        new Thread(this, s).start();
    }

    @Override
    public void run() {
        phaser.register();//registering this thread
        System.out.println("------- ***************** -------");
        print("--- after registering ---");
        System.out.println("------- ***************** -------");
        for (int i = 1; i <= 2; i++) {
            try {
                Thread.sleep(5000);
                Task8.main();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //the barrier point
            System.out.println("------- ***************** -------");
            print("-> before arrive " + i);
            phaser.arriveAndAwaitAdvance();//current thread will wait for others to arrive
            print("-> after arrive " + i);
            System.out.println("------- ***************** -------");
        }
    }

    private void print(String msg) {
        System.out.printf("%-20s: %10s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
                msg,
                Thread.currentThread().getName(),
                phaser.getRegisteredParties(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties(),
                phaser.getPhase()
        );
    }
}

class Phas2 implements Runnable {

    Phaser phaser;

    public Phas2(Phaser phaser, String s) {
        this.phaser = phaser;
        new Thread(this, s).start();
    }

    @Override
    public void run() {
        phaser.register();//registering this thread
        print("--- after registering ---");
        System.out.println("------- ***************** -------");
        for (int i = 1; i <= 2; i++) {
            try {
                Task31.main();
                Thread.sleep(3000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            //the barrier point
            System.out.println("------- ***************** -------");
            print("-> before arrive " + i);
            phaser.arriveAndAwaitAdvance();//current thread will wait for others to arrive
            print("-> after arrive " + i);
            System.out.println("------- ***************** -------");
        }
    }

    private void print(String msg) {
        System.out.printf("%-20s: %10s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
                msg,
                Thread.currentThread().getName(),
                phaser.getRegisteredParties(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties(),
                phaser.getPhase()
        );
    }
}
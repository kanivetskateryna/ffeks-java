package com.dnu.ffeks.multithreading.callable;

import com.dnu.ffeks.lab3.Task8.Task8;
import com.dnu.ffeks.multithreading.Lab2;

import java.io.IOException;
import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CallLab2 lab2 = new CallLab2();
        Future<Boolean> future = executorService.submit(lab2);
        if (future.get()) {
            System.out.println();
            new Thread(new CallLab3());
        }
        executorService.shutdown();
    }
}

class CallLab2 implements Callable<Boolean> {

    @Override
    public Boolean call() throws IOException {
        Lab2.Runner runner = new Lab2.Runner();
        return runner.isPasswordStrong("Passw");
    }
}

class CallLab3 implements Runnable {

    public CallLab3() {
        new Thread(this, "lab-3").start();
    }

    @Override
    public void run() {
        Task8.main();
    }
}

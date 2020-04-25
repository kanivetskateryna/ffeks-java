package com.dnu.ffeks.modelingofsystems.var11;

public class SystemModeling11 {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        int modelingTime = 3000;
        ComputingSystem computingSystem = new ComputingSystem(modelingTime);
        computingSystem.startWork();
    }
}

package com.dnu.ffeks.modelingofsystems;

public class ModelingSystem {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        int modelingTime = 1400;
        WorkshopAssemblyArea workshopAssemblyArea = new WorkshopAssemblyArea(modelingTime);
        workshopAssemblyArea.startModeling();
    }
}

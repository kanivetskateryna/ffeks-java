package com.dnu.ffeks.lab4.task6;

import javax.swing.*;

/**
 * Зобразити чотирикутник, що обертається в площині апплета навколо свого центру ваги.
 */
public class Task6 {

    private static final int LOCATION_X = 820;
    private static final int LOCATION_Y = 350;

    public static void main() {
        JFrame frame = new QuadrilateralFrame(300, 300, LOCATION_X, LOCATION_Y);
        frame.setTitle("Вращение треугольника вокруг своего центра тяжести");
        JPanel panel = new QuadrilateralPanel();
        frame.add(panel);

        int counter = 0;
        RotationAction rotationAction = new RotationAction(panel, counter);
        rotationAction.setXPosition(60, -80, 50);
        rotationAction.setYPosition(-60, -50, 40);
        Timer tm = new Timer(10, rotationAction);
        tm.start();
    }
}

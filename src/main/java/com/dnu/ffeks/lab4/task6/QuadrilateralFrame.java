package com.dnu.ffeks.lab4.task6;

import javax.swing.*;
import java.awt.*;

public class QuadrilateralFrame extends JFrame {

    public QuadrilateralFrame(int width, int height, int xLocation, int yLocation) {
        super.setPreferredSize(new Dimension(width, height));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setLocation(xLocation, yLocation);
        super.pack();
    }
}

package com.dnu.ffeks.lab4.task6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class RotationAction implements ActionListener {

    private JPanel panel;
    private static final int COUNT_OF_SIDES = 3;

    private int side1x;
    private int side2x;
    private int side3x;

    private int side1y;
    private int side2y;
    private int side3y;

    private int counter;

    private final int anchorX = (side1x + side2x + side3x) / COUNT_OF_SIDES;
    private final int anchorY = (side1y + side2y + side3y) / COUNT_OF_SIDES;

    public RotationAction(JPanel panel, int counter) {
        this.panel = panel;
        this.counter = counter;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Graphics2D gr = (Graphics2D) panel.getRootPane().getGraphics();
        panel.update(gr);
        GeneralPath path = new GeneralPath();
        path.append(createPolygon(), true);
        gr.translate(150, 150);
        AffineTransform tranforms = AffineTransform.getRotateInstance((counter++) * 0.07, anchorX, anchorY);
        gr.transform(tranforms);
        gr.draw(path);
    }

    private Polygon createPolygon() {
        return new Polygon(new int[]{side1x, side2x, side3x}, new int[]{side1y, side2y, side3y}, COUNT_OF_SIDES);
    }

    public void setXPosition(int x, int y, int z) {
        this.side1x = x;
        this.side2x = y;
        this.side3x = z;
    }

    public void setYPosition(int x, int y, int z) {
        this.side1y = x;
        this.side2y = y;
        this.side3y = z;
    }
}

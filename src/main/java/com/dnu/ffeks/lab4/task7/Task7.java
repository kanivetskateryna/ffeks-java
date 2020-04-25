package com.dnu.ffeks.lab4.task7;

import com.google.common.io.Resources;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Створити фрейм із областю для малювання «пером». Створити меню для вибору кольору й товщини лінії.
 */
public class Task7 {

    public static void main() {
        start();
    }

    private static void start() {
        invokeLater(() -> {
            Image img = new ImageIcon(Resources.getResource("paint-palette.png")).getImage();
            MyPaint myPaint = new MyPaintImpl("My Paint");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            myPaint.setProperties(img, screenSize);
        });
    }
}

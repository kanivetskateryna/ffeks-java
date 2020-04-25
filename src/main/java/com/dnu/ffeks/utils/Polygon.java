package com.dnu.ffeks.utils;

import java.awt.Point;

public class Polygon {

    private int numberOfPoints;
    private Point[] points;

    public Polygon() {
        numberOfPoints = 0;
        points = new Point[6];
    }

    private void resize() {
        Point[] temp = new Point[2 * numberOfPoints + 1];
        if (numberOfPoints + 1 >= 0) {
            System.arraycopy(points, 0, temp, 0, numberOfPoints + 1);
        }
        points = temp;
    }

    public void addPoint(Point point) {

        // working like resizing in arraylist
        if (numberOfPoints >= points.length - 1) {
            resize();
        }

        points[numberOfPoints++] = point;
    }

    // Shoelace formula
    public double getArea() {

        double area = 0.0;

        for (int i = 0; i < numberOfPoints - 1; i++) {
            area = area + (points[i].x * points[i + 1].y) - (points[i].y * points[i + 1].x);
        }
        area += points[numberOfPoints - 1].x * points[0].y - points[0].x * points[numberOfPoints - 1].y;

        area *= 0.5;

        if (area < 0) {
            area *= -1;
        }

        return area;
    }

    @Override
    public String toString() {
        if (numberOfPoints == 0) return "[ ]";
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i <= numberOfPoints; i++) {

            if (points[i] == null) {
                break;
            }

            str.append("x=")
                    .append((int) points[i].getX())
                    .append(", ")
                    .append("y=")
                    .append((int) points[i].getY());
            if (i != numberOfPoints) {
                str.append(String.format("%-12s", ",\n"));
            }
        }
        str.append("]");
        return str.toString();
    }
}

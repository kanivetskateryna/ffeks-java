package com.dnu.ffeks.utils;

public class Triangle {

    private Point3D a;
    private Point3D b;
    private Point3D c;

    public Triangle(Point3D a, Point3D b, Point3D c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private double sideAB() {
        return this.a.distanceTo(this.b);
    }

    private double sideBC() {
        return this.b.distanceTo(this.c);
    }

    private double sideAC() {
        return this.a.distanceTo(this.c);
    }

    public double getPerimeter() {
        return sideAB() + sideAC() + sideBC();
    }

    @Override
    public String toString() {
        return "Points: " + String.format("%s, %s, %s;", this.a, this.b, this.c) + "\n" +
                "Lengths of the sides: " + String.format("%.3f, %.3f, %.3f;", sideAB(), sideBC(), sideAC()) + "\n" +
                "Perimeter: " + String.format("%.3f;", getPerimeter()) + "\n";
    }
}

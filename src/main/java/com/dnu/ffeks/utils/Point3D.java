package com.dnu.ffeks.utils;


public class Point3D {

    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("[%.3f, %.3f, %.3f]", this.x, this.y, this.z);
    }

    double distanceTo(Point3D point) {
        double anotherX = Math.pow(point.getX() - this.x, 2);
        double anotherY = Math.pow(point.getY() - this.y, 2);
        double anotherZ = Math.pow(point.getZ() - this.z, 2);
        return Math.sqrt(anotherX + anotherY + anotherZ);
    }
}

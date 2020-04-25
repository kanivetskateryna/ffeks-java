package com.dnu.ffeks.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point2D {

    private int x;
    private int y;

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }

    public double distanceTo(Point3D point) {
        double anotherX = Math.pow(point.getX() - this.x, 2);
        double anotherY = Math.pow(point.getY() - this.y, 2);
        return Math.sqrt(anotherX + anotherY);
    }
}

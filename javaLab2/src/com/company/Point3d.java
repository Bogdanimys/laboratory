package com.company;

public class Point3d {

    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3d() {
        this(0, 0, 0);
    }

    public double getX () {
        return xCoord;
    }
    public double getY () {
        return yCoord;
    }
    public double getZ () {
        return zCoord;
    }

    public void setX ( double val) {
        xCoord = val;
    }
    public void setY ( double val) {
        yCoord = val;
    }
    public void setZ ( double val) {
        zCoord = val;
    }

    public double distanceTo (Point3d otherPoint){
        return Math.floor(100 * Math.sqrt(
                (xCoord - otherPoint.getX()) * (xCoord - otherPoint.getX()) +
                (yCoord - otherPoint.getY()) * (yCoord - otherPoint.getY()) +
                (zCoord - otherPoint.getZ()) * (zCoord - otherPoint.getZ()) )) / 100;
    }

    public boolean equalsTo (Point3d otherPoint){
        return (xCoord == otherPoint.getX() &&
                yCoord == otherPoint.getY() &&
                zCoord == otherPoint.getZ());
    }
}

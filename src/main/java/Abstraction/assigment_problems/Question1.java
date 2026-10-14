
package Abstraction.assignment_problems;

abstract class Shape {
    private static int counter = 1001;
    private final String shapeId;
    private double scaleX = 1.0;
    private double scaleY = 1.0;

    public Shape() {
        shapeId = "SH-" + counter++;
    }

    public String getShapeId() {
        return shapeId;
    }

    protected double getScaleX() {
        return scaleX;
    }

    protected double getScaleY() {
        return scaleY;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        scale(factor, factor);
    }

    public void scale(double xFactor, double yFactor) {
        scaleX *= xFactor;
        scaleY *= yFactor;
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius
                * getScaleX() * getScaleY();
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side * getScaleX() * getScaleY();
    }
}

public class Question1 {
    static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        printArea(c);
        printArea(sq);

        sq.scale(2.0);
        System.out.println(sq.calculateArea()); // 64.0

        sq.scale(2.0, 3.0);
        System.out.println(sq.calculateArea()); // 768.0
    }
}

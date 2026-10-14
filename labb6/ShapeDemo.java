
// Exercise 1 (basic): abstract Shape with abstract area() and perimeter()
abstract class Shape {
    abstract double area();         // no body: each concrete shape knows its own formula
    abstract double perimeter();

    // concrete method shared by all shapes; it works for any subclass
    void describe() {
        System.out.printf("%s -> area = %.2f, perimeter = %.2f%n", getClass().getSimpleName(), area(), perimeter());
    }
}

class Circle extends Shape {
    private final double radius;
    Circle(double radius) { this.radius = radius; }

    @Override double area() { return Math.PI * radius * radius; }
    @Override double perimeter() { return 2 * Math.PI * radius; }   // circumference
}

class Rectangle extends Shape {
    private final double width, height;
    Rectangle(double width, double height) { this.width = width; this.height = height; }

    @Override double area() { return width * height; }
    @Override double perimeter() { return 2 * (width + height); }
}

class Square extends Shape {
    private final double side;
    Square(double side) { this.side = side; }

    @Override double area() { return side * side; }
    @Override double perimeter() { return 4 * side; }
}

public class ShapeDemo {
    public static void main(String[] args) {
        // Shape s = new Shape();   // COMPILE ERROR: Shape is abstract
        Shape[] shapes = { new Circle(5), new Rectangle(4, 6), new Square(3) };

        for (Shape s : shapes) {
            s.describe();           // one call, three different behaviours (polymorphism)
        }
    }
}
import shapes.Rectangle;
import shapes.Triangle;

public class Client {
    public static void main(String[] args) {
        testTriangle();
        testRectangle();
        testCircle();
    }

    public static void testTriangle() {
        // Create triangles
        Triangle t1 = new Triangle();
        Triangle t2 = new Triangle(27, 12, 13.5);
        Triangle t3 = new Triangle(9, 7.5, 3.1);
        Triangle t4 = new Triangle(8.54, 3, 8);
        Triangle t5 = new Triangle(4, 7.25, 4);

        // Display information
        t1.displayTriangle();
        System.out.println(t1.isTriangle());

        t2.displayTriangle();
        System.out.println(t2.isTriangle());

        t2.setLongestSide(2 + t2.getMediumSide()); // Update longest side to the length of the medium side
        System.out.println(t2.isTriangle());
        System.out.println("Perimeter: " + t3.perimeter());
        System.out.println("Area: " + t3.area());
        System.out.println(t4.isRightTriangle());
        t5.displayTriangle();
    }

    public static void testRectangle() {
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle(12.5, 8);
        Rectangle r3 = new Rectangle(4.5, 4.45);
        r1.displayRectangle();
        System.out.println(r1.isSquare());
        r2.displayRectangle();
        System.out.println(r2.area());
        System.out.println(r2.perimeter());
        System.out.println(r3.isSquare());
        r3.setLength(6.25);
        r3.setWidth(5.75);
        System.out.println(r3.isSquare());
    }
    public static void testCircle() {
        Circle c1 = new Circle();
        Circle c2 = new Circle(3.5);
        Circle c3 = new Circle(-2);
        c1.displayCircle();
        System.out.println(c1.isUnitCircle());
        System.out.println(c2.area());
        System.out.println(c2.circumference());
        System.out.println(c3.getRadius());
        c2.setRadius(1.08);
        System.out.println(c2.isUnitCircle());
    }
}

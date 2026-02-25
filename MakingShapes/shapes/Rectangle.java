package shapes;

/** A Rectangle shape */
public class Rectangle {
    // Side lengths
    private double length;
    private double width;

    private static final String DISPLAY_FORMAT = String.join("\n", 
        "Length: %s",
        "Width: %s"
    ) + "\n";

    /**
     * Creates a new rectangle with the provided side lengths
     * @param length The length of the rectangle
     * @param width The length of the rectangle
     */
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    /** Creates a new 5x3 rectangle */
    public Rectangle() {
        this(5.0, 3.0);
    }

    /**
     * Sets the length of the rectangle
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /** @return the length of the rectangle */
    public double getLength() {
        return length;
    }

    /**
     * Sets the width of the rectangle
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /** @return the width of the rectangle */
    public double getWidth() {
        return width;
    }

    /** @return the perimeter of the rectangle via 2 * (length + width) */
    public double perimeter() {
        return 2 * (length + width);
    }

    /** @return the area of the rectangle via length * width */
    public double area() {
        return length * width;
    }

    /**
     * Prints the side lengths of rectangle in the following format:
     * <ul>
     * <li>Length: length of the rectangle</li>
     * <li>Width: width of the rectangle</li>
     * </ul>
     */
    public void displayRectangle() {
        System.out.printf(DISPLAY_FORMAT, length, width);
    }
}
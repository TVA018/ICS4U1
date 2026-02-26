package shapes;

/** A Circle shape */
public class Circle {
    private double radius;

    private static final String DISPLAY_FORMAT = "Radius: %s\n";

    /**
     * Creates a new circle with the provided side radius. If the radius is negative, the radius will be set to 1.0
     * @param radius The radius of the circle
     */
    public Circle(double radius) {
        this.radius = (radius >= 0) ? radius : 1.0;
    }

    /** Creates a new circle with a radius of 1.0 */
    public Circle() {
        this(1.0);
    }

    /**
     * Sets the radius of the circle
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /** @return the radius of the circle */
    public double getRadius() {
        return radius;
    }

    /** @return the diameter of the circle */
    public double diameter() {
        return radius * 2;
    }

    /** @return the circumference of the circle via 2πr. Rounds to the nearest integer */
    public int circumference() {
        return (int) Math.round(2 * Math.PI * radius);
    }

    /** @return the area of the circle via πr^2. Rounds to the nearest integer */
    public int area() {
        return (int) Math.round(Math.PI * radius * radius);
    }

    /** @return whether the radius of the circle is 0.1 within 1 */
    public boolean isUnitCircle() {
        return Math.abs(1.0 - radius) <= 0.1;
    }

    /**
     * Prints the side radius of circle in the following format:
     * <ul>
     * <li>Radius: radius of the circle</li>
     * </ul>
     */
    public void displayCircle() {
        System.out.printf(DISPLAY_FORMAT, radius);
    }
}
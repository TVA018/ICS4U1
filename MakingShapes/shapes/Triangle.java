package shapes;

import java.util.Arrays;

/** A triangle shape */
public class Triangle {
    // Side lengths
    private double shortestSide;
    private double mediumSide;
    private double longestSide;

    private static final String DISPLAY_FORMAT = String.join("\n", 
        "Shortest Side: %s",
        "Medium Side: %s",
        "Longest Side: %s"
    ) + "\n";

    /**
     * Creates a new triangle with the provided side lengths. The order the sides are provided in do not matter.
     * @param side1 The length of any unique side
     * @param side2 The length of any unique side
     * @param side3 The length of any unique side
     */
    public Triangle(double side1, double side2, double side3) {
        double[] sortedSides = {side1, side2, side3};
        Arrays.sort(sortedSides);

        shortestSide = sortedSides[0];
        mediumSide = sortedSides[1];
        longestSide = sortedSides[2];
    }

    /** Creates a new triangle with side lengths 3, 4, 5 */
    public Triangle() {
        this(3, 4, 5);
    }

    /**
     * Sets the shortest side length to the specified length
     * @param length
     */
    public void setShortestSide(double length) {
        this.shortestSide = length;
    }

    /** @return the length of the shortest side */
    public double getShortestSide() {
        return shortestSide;
    }

    /**
     * Sets the medium side length to the specified length
     * @param length
     */
    public void setMediumSide(double length) {
        this.mediumSide = length;
    }

    /** @return the length of the medium side */
    public double getMediumSide() {
        return mediumSide;
    }

    /**
     * Sets the longest side length to the specified length
     * @param length
     */
    public void setLongestSide(double length) {
        this.longestSide = length;
    }

    /** @return the length of the longest side */
    public double getLongestSide() {
        return longestSide;
    }

    /** @return whether the triangle is a valid triangle */
    public boolean isTriangle() {
        return (shortestSide + mediumSide) > longestSide;
    }

    /** @return whether the triangle is a right triangle. If c^2 - (a^2 + b^2) is within 0.1, then the triangle is considered to be
     * a right triangle
     */
    public boolean isRightTriangle() {
        return Math.abs((shortestSide * shortestSide + mediumSide * mediumSide) - (longestSide * longestSide)) <= 0.1; 
    }

    /** @return the perimeter of the triangle via (side1 + side2 + side3) */
    public double perimeter() {
        return shortestSide + mediumSide + longestSide;
    }

    /** @return the area of the triangle via <a href="https://en.wikipedia.org/wiki/Heron%27s_formula">Heron's formula</a> */
    public double area() {
        double halfPerimeter = perimeter() / 2;

        return Math.sqrt(halfPerimeter * (halfPerimeter - shortestSide) * (halfPerimeter - mediumSide) * (halfPerimeter - longestSide));
    }

    /**
     * Prints the side lengths of the triangle in the format:
     * <ul>
     * <li>Shortest Side: length of shortest side</li>
     * <li>Medium Side: length of medium side</li>
     * <li>Longest Side: length of longest side</li>
     * </ul>
     */
    public void displayTriangle() {
        System.out.printf(DISPLAY_FORMAT, shortestSide, mediumSide, longestSide);
    }
}
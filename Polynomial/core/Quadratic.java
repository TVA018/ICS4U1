package core;

public class Quadratic extends Polynomial {
    public final double discriminant;

    private final double a;
    private final double b;
    private final double c;

    /**
     * Constructs a quadratic expression with the specified a, b, and c coefficients in the standard form ax^2 + bx + c
     * @param a Coefficient of the second degree term
     * @param b Coefficient of the first degree term
     * @param c The constant 
     */
    public Quadratic(double a, double b, double c) {
        if(a == 0) throw new IllegalArgumentException("a must be non-zero in a quadratic.");

        super(
            new PolynomialTerm(a, 2),
            new PolynomialTerm(b, 1),
            new PolynomialTerm(c, 0)
        );

        this.a = a;
        this.b = b;
        this.c = c;

        discriminant = b * b - (4 * a * c);
    }

    /** @return Whether the quadratic has real solutions */
    public boolean hasRealSolutions() {
        return discriminant >= 0;
    }

    /** @return The root calculated by adding the positive square root of the discriminant */
    public double firstZero() {
        return (-b + Math.sqrt(discriminant)) / (2 * a);
    }

    /** @return The root calculated by adding the positive square root of the discriminant */
    public double secondZero() {
        return (-b - Math.sqrt(discriminant)) / (2 * a);
    }

    /** @return Whether the quadratic is in the form (a + b)^2 */
    public boolean isPerfectSquare() {
        if(a < 0) return false;
        if(c < 0) return false;

        double sqrtA = Math.sqrt(a);
        double sqrtC = Math.sqrt(c);

        return b == (2 * sqrtA * sqrtC);
    }

    /** @return The x-coordinate of the vertex */
    public double getVertexX() {
        return -b / (2 * a);
    }

    /** @return Whether this quadratic is a trinomial */
    public boolean isTrinomial() {
        return terms.length == 3;
    }
}

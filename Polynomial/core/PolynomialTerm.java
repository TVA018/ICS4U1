package core;

public record PolynomialTerm(double coefficient, int degree) {
    public PolynomialTerm {
        if(degree < 0) throw new IllegalArgumentException("The degree of a polynomial term must be non-negative");
    }

    /** Merges two polynomial terms of the same degree into one term by adding their coefficients */
    public PolynomialTerm merge(PolynomialTerm other) {
        if(degree != other.degree) throw new IllegalArgumentException("Cannot merge two polynomial terms of different degrees");

        return new PolynomialTerm(coefficient + other.coefficient, degree);
    }

    /** @return the y value of the given x value for this polynomial term */
    public double getY(double x) {
        return coefficient * Math.pow(x, degree);
    }

    @Override
    public final String toString() {
        return String.format(
            "%s%s%s", 
            (coefficient != 1 || degree == 0) ? coefficient : "", // Output the coefficient only if it isn't 1 or the degree is 0 (constant)
            degree > 0 ? "x" : "", // Output "x" only if it isn't 0 (a constants)
            degree > 1 ? "^" + String.valueOf(degree) : "" // Output the exponent only if it isn't 0 or 1
        );
    }
}
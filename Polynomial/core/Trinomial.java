package core;

public class Trinomial extends Polynomial {
    /**
     * Constructs a new trinomial with the specified terms (order doesn't matter, it will be automatically reordered).
     * All of the terms must be of a different degree
     * @param term1 a term
     * @param term2 a term
     * @param term3 a term
     */
    public Trinomial(PolynomialTerm term1, PolynomialTerm term2, PolynomialTerm term3) {
        super(term1, term2, term3);

        if (terms.length < 3)
            throw new IllegalArgumentException(toString() + ": Trinomial must have 3 terms");
    }

    /** @return return the coefficient of the term of the highest degree */
    public double getLeadingCoefficient() {
        return terms[0].coefficient();
    }

    /** @return return the coefficient of the term of the middle degree */
    public double getMiddleCoefficient() {
        return terms[1].coefficient();
    }

    /** @return return the coefficient of the term of the lowest degree */
    public double getLastCoefficient() {
        return terms[2].coefficient();
    }
}
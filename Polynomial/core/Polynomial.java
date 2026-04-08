package core;
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    protected final PolynomialTerm[] terms;
    protected final String expression;

    public Polynomial(PolynomialTerm ...terms) {
        if(terms.length == 0) throw new IllegalArgumentException("There must be at least 1 term for there to be a polynomial expression");

        List<PolynomialTerm> rawTermsList = new ArrayList<>(List.of(terms));

        // Sort terms by highest to lowest degree
        rawTermsList.sort((a, b) -> b.degree() - a.degree());

        // Automatically merge terms with the same degree
        List<PolynomialTerm> mergedTerms = new ArrayList<>();

        PolynomialTerm previousTerm = null;

        for(PolynomialTerm term: rawTermsList) {
            // If there is a previous term
            if(previousTerm != null) {
                // Merge the terms if they are the same degree
                if(previousTerm.degree() == term.degree()) {
                    previousTerm = term.merge(previousTerm);
                } else {
                    // Add the previous term only if it isn't 0
                    if(previousTerm.coefficient() != 0) {
                        mergedTerms.add(previousTerm);
                    }

                    // Set the previous term to this current term
                    previousTerm = term;
                }
            } else { // Starting previousTerm
                previousTerm = term;
            }
        }

        // Add the term only if it isn't 0
        if(previousTerm.coefficient() != 0) {
            mergedTerms.add(previousTerm);
        }

        this.terms = mergedTerms.toArray(PolynomialTerm[]::new);

        // Create expression
        StringBuilder expressionBuilder = new StringBuilder(this.terms[0].toString());

        for(int i = 1; i < this.terms.length; i++) {
            PolynomialTerm term = this.terms[i];

            expressionBuilder
                .append(term.coefficient() < 0 ? " - " : " + ")
                // make the coefficient positive because the sign is represented by the previous symbol
                .append(new PolynomialTerm(Math.abs(term.coefficient()), term.degree()).toString());
        }

        expression = expressionBuilder.toString();
    }

    /** @return the y value for the given x value. Equivalent to f(x) when f is this polynomial expression */
    public double getY(double x) {
        double sum = 0;

        for(PolynomialTerm term: terms)
            sum += term.getY(x);

        return sum;
    }
    
    /**
     * Gets the term at the specified position termIndex
     * @param termIndex the index of the term to grab, 0-indexed
     * @return the term
     */
    public PolynomialTerm getTerm(int termIndex) {
        return terms[termIndex];
    }

    /** @return the number of terms in this polynomial expression */
    public int getNumberOfTerms() {
        return terms.length;
    }

    @Override
    public String toString() {
        return String.format("Degree %s, %s terms\n%s", terms[0].degree(), getNumberOfTerms(), expression);
    }

    @Override
    public boolean equals(Object object) {
        // Simple checks to skip obvious cases
        if(this == object) return true;
        if(object == null) return false;
        if(!(object instanceof Polynomial)) return false; // Allow this to compare with its subclasses too

        // Comparing two polynomials that don't point to the same instance
        Polynomial otherPolynomial = (Polynomial) object;

        // Check obvious case
        if(terms.length != otherPolynomial.terms.length) return false;

        // Return false if any of the terms are not equal.
        // We know that the terms are in the correct order no matter what due to the constructor,
        // so this.terms[i] should equal otherPolynomia.terms[i] for every valid value of i if the two
        // polynomials are the same expression
        for(int i = 0; i < terms.length; i++) {
            if(!terms[i].equals(otherPolynomial.terms[i]))
                return false;
        }

        // All checks passed, the two expressions are the same
        return true;
    }
}
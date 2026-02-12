/**
 * Represents the quadratic formula (-b ± sqrt(b^2 - 4ac))/(2a) for a given quadratic expression ax^2 + bx + c
*/
public class QuadraticFormula {
    private final double discriminant;
    private final double root1;
    private final double root2;

    private final String quadraticExpression;

    /** 
     * Creates a quadratic formula for the expression ax^2 + bx + c 
     * @param a The a coefficient, must be non-zero
     * @param b The b coefficient
     * @param c The c coefficient
     * */
    public QuadraticFormula(double a, double b, double c) {
        // Values can be pre-calculated because a, b, and c are constant
        this.discriminant = (b * b) - (4 * a * c);

        // Guard clauses
        if(discriminant < 0) throw new IllegalArgumentException("Can't use the quadratic formula: The discriminant is negative.");
        if(a == 0) throw new IllegalArgumentException("Can't use the quadratic formula: 'a' must be a non-zero value");
        // Guard clauses end

        double denominator = 2 * a;
        double rootDiscriminant = Math.sqrt(discriminant);
        this.root1 = (-b + rootDiscriminant) / denominator;
        this.root2 = (-b - rootDiscriminant) / denominator;

        // Create quadratic expression representation
        StringBuilder expressionBuilder = new StringBuilder();

        if(a != 1.0) expressionBuilder.append(a);
        expressionBuilder.append("x^2");

        if(b != 0) {
            String sign = (b < 0) ? " - " : " + ";
            expressionBuilder.append(sign);
            if(b != 1.0) expressionBuilder.append(Math.abs(b));
            expressionBuilder.append("x");
        }
        
        if(c != 0) {
            String sign = (c < 0) ? " - " : " + ";
            expressionBuilder.append(sign);
            if(c != 1.0) expressionBuilder.append(Math.abs(c));
        }

        this.quadraticExpression = expressionBuilder.toString();
    }

    /** Creates a quadratic formula for the expression x^2 + x + 1 */
    public QuadraticFormula() {
        this(1.0, 0.0, 0.0);
    }

    /** 
     * Returns the first root of the quadratic formula (the root where it uses the positive square root of the discriminant)
     * @return the first root
     */
    public double calculate1() {
        return root1;
    }

    /** 
     * Returns the second root of the quadratic formula (the root where it uses the negative square root of the discriminant)
     * @return the second root
     */
    public double calculate2() {
        return root2;
    }

    /** 
     * Returns the discriminant
     * @return the discriminant
     */
    public double getDiscriminant() {
        return discriminant;
    }

    /** 
     * Returns the quadratic expression this formula is calculating
     * @return The quadratic expression as text
     * */
    public String getQuadraticExpression() {
        return quadraticExpression;
    }
}

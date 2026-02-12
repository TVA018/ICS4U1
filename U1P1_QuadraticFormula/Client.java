public class Client {
    /** Represents the quadratic expression ax^2 + bx + c */
    public record QuadraticExpression(double a, double b, double c) {}

    public static QuadraticExpression[] testExpressions = {
        new QuadraticExpression(1.0, 1.0, 0.0),
        new QuadraticExpression(2.0, 1.0, 0.0),
        new QuadraticExpression(1.0, 1.0, -1.0),
        new QuadraticExpression(1.0, 2.0, -1.0),
        new QuadraticExpression(2.0, 3.0, -100.0),
        new QuadraticExpression(4.0, 0.0, -100.0),
        new QuadraticExpression(-2.0, 3.0, 10.0)
    }; 

    public static void main(String[] args) {
        // Test default
        testFormula(new QuadraticFormula());

        // Test expressions
        for(QuadraticExpression expression: testExpressions) {
            testFormula(new QuadraticFormula(expression.a(), expression.b(), expression.c()));
        }
    }

    /** Tests the functions of a quadratic formula */
    public static void testFormula(QuadraticFormula formula) {
        // Print the info
        System.out.printf("Expression: %s\n\n", formula.getQuadraticExpression());

        // Test the public functions
        System.out.printf("Discriminant: %s\n", formula.getDiscriminant());
        System.out.printf("First Root: %s\n", formula.calculate1());
        System.out.printf("Second Root: %s\n", formula.calculate2());
        System.out.println();
    }
}

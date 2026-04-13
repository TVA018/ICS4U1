import core.Polynomial;
import core.PolynomialTerm;
import core.Quadratic;
import core.Trinomial;

public class Main {
    public static void main(String[] args) {
        // Polynomial testing
        Polynomial polly = new Polynomial(
            new PolynomialTerm(5, 1),
            new PolynomialTerm(7, 0)
        );

        System.out.println("POLLY");
        System.out.println(polly);
        System.out.println();

        // Trinomial testing
        Trinomial trinity = new Trinomial(
            new PolynomialTerm(3, 4), 
            new PolynomialTerm(-5, 3), 
            new PolynomialTerm(1, 2)
        );

        System.out.println("TRINITY");
        System.out.println(trinity);
        System.out.println();

        // Quadratic testing
        Quadratic quinn = new Quadratic(2, 5, -3);

        System.out.println("QUINN");
        System.out.println(quinn);
        System.out.println();

        // Equivalence testing
        Polynomial pollySister = new Polynomial(
            new PolynomialTerm(5, 1),
            new PolynomialTerm(7, 0)
        );

        System.out.printf("Polly equals Polly sister: %s\n", polly.equals(pollySister)); 
        
        Trinomial trinityBrother = new Trinomial(
            new PolynomialTerm(3, 4), 
            new PolynomialTerm(-5, 3), 
            new PolynomialTerm(1, 2)
        );

        System.out.printf("Trinity equals Trinity brother: %s\n", trinity.equals(trinityBrother)); 
        Quadratic quinnSister = new Quadratic(2, 5, -3);
        System.out.printf("Quinn equals Quinn sister: %s\n", quinn.equals(quinnSister)); 
        System.out.printf("Quinn == Quinn sister: %s\n", quinn == quinnSister); 

        // Special methods testing
        System.out.printf("Quinn has real solutions: %s\n", quinn.hasRealSolutions()); 
        System.out.printf("Quinn's first zero: %s\n", quinn.firstZero());
        System.out.printf("Quinn's second zero: %s\n", quinn.secondZero());
        System.out.printf("Quinn is a perfect square: %s\n", quinn.isPerfectSquare());
        Quadratic quinton = new Quadratic(9, 12, 4);
        System.out.printf("Quinton is a perfect square: %s\n", quinton.isPerfectSquare());

        Quadratic bino = new Quadratic(2, 0, 5);

        System.out.printf("Quinton's vertex is at: (%s, %s)\n", quinton.getVertexX(), quinton.getY(quinton.getVertexX()));
        System.out.printf("Is Quinton a trinomial: %s\n", quinton.isTrinomial());
        System.out.printf("Is Bino a trinomial: %s\n", bino.isTrinomial());
    }
}

import core.Polynomial;
import core.PolynomialTerm;
import core.Trinomial;

public class Test {
    public static void main(String[] args) {
        // System.out.println(new PolynomialTerm(1, 0));
        // System.out.println(new PolynomialTerm(1, 1));
        // System.out.println(new PolynomialTerm(1, 2));
        // System.out.println(new PolynomialTerm(2, 2));

        Polynomial polynomial = new Polynomial(
            new PolynomialTerm(-4, 2),
            new PolynomialTerm(2, 2),
            new PolynomialTerm(1, 1),
            new PolynomialTerm(-5, 0)
        );

        Trinomial trinomial = new Trinomial(
            new PolynomialTerm(-2, 2),
            new PolynomialTerm(1, 1),
            new PolynomialTerm(-5, 0)
        );

        System.out.println(polynomial.getY(1.5));
        System.out.println(trinomial.getY(5));

        System.out.println(polynomial);
        System.out.println(trinomial);
        System.out.println(trinomial.equals(polynomial));
    }
}

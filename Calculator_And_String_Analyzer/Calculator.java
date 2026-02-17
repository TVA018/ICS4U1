public class Calculator {
    public final double num1;
    public final double num2;

    private final double sum;
    private final double difference;
    private final double product;
    private final double quotient;

    private final double powerResult;
    private final double absDiff;
    private final double sqrtOfSum;

    public Calculator(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;

        // Precalculate values
        this.sum = num1 + num2;
        this.difference = num1 - num2;
        this.product = num1 * num2;
        this.quotient = num1 / num2; // Assume num2 is non-zero

        this.powerResult = Math.pow(num1, num2);
        this.absDiff = Math.abs(this.difference);
        this.sqrtOfSum = Math.sqrt(this.sum); // Assume the sum is not negative
    }

    public Calculator() {
        this(1, 1);
    }

    /** @return the sum of num1 and num2 */
    public double add() {
        return sum;
    }

    /** @return the difference when num2 is subtracted from num1 */
    public double subtract() {
        return difference;
    }

    /** @return the product of num1 and num2 */
    public double multiply() {
        return product;
    }

    /** @return the quotient of num1 divided by num2 */
    public double divide() {
        return quotient;
    }

    /** @return num1 raised to num2 */
    public double power() {
        return powerResult;
    }

    /** @return the absolute (positive) difference of num1 and num2 */
    public double absoluteDifference() {
        return absDiff;
    }

    /** @return the square root of the sum of num1 and num2 */
    public double squareRootOfSum() {
        return sqrtOfSum;
    }

    /** @return a random number between num1 (inclusive) and num2 (exclusive) */
    public double randomBetweenNums() {
        double offset = Math.random() * -difference; // multiply by negative difference so that it is (num2 - num1)

        return num1 + offset;
    }
}
public class Client {
    private static final ValidatedScanner.StringInputParser<Integer> num1Validator = str -> Integer.parseInt(str);
    private static final ValidatedScanner.StringInputParser<Integer> num2Validator = str -> {
        int num = Integer.parseInt(str);

        if(num == 0) throw new RuntimeException("The number cannot be 0");

        return num;
    };

    public static void main(String[] args) {
        if(!testDefaults()) return;

        ValidatedScanner scanner = new ValidatedScanner();

        Calculator calculator = new Calculator(
            scanner.readInput("Enter in the first number: ", num1Validator),
            scanner.readInput("Enter in the second number: ", num2Validator)
        );

        StringManipulator manipulator = new StringManipulator(
            scanner.readLine("Enter first word: "),
            scanner.readLine("Enter second word: ")
        );

        // Output results
        System.out.printf("Sum: %s\n", calculator.add());
        System.out.printf("Difference: %s\n", calculator.subtract());
        System.out.printf("Product: %s\n", calculator.multiply());
        System.out.printf("Quotient: %s\n", calculator.divide());
        System.out.printf("Power: %s\n", calculator.power());
        System.out.printf("Absolute Difference: %s\n", calculator.absoluteDifference());
        System.out.printf("Square Root of Sum: %s\n", calculator.squareRootOfSum());
        System.out.printf("Random Number: %s\n", calculator.randomBetweenNums());

        System.out.println();

        System.out.printf("Combined: %s\n", manipulator.combineStrings());
        System.out.printf("Combined Length: %s\n", manipulator.getCombinedLength());
        System.out.printf("First Half: %s\n", manipulator.firstHalf());
        System.out.printf("Index of letter: %s\n", manipulator.indexOfFirstLetter());
        System.out.printf("Swapped: %s\n", manipulator.swapFirstAndSecondHalf());
    }

    /** 
     * Tests the default constructors of Calculator and StringManipulator 
     * @return Whether the tests were passed
     * */
    private static boolean testDefaults() {
        boolean testsPassed = true;

        // Test calculator
        Calculator calculator = new Calculator();

        if(Tester.test(calculator.num1 + calculator.num2, calculator.add(), "Sum method failed")) testsPassed = false;

        return testsPassed;
    }
}

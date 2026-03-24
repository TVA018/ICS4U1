import util.TerminalTextFormatter;
import util.ValidatedScanner;
import util.TerminalTextFormatter.ANSIFlag;

/** Static class to prompt simple math questions */
public final class MathQuestionaire {
    private MathQuestionaire() {}

    private interface IntegerOperation {
        public abstract int calculate(int ls, int rs);
    }

    private record IntegerOperator(String symbol, IntegerOperation operation) {}

    /** The possible operands */
    private static final IntegerOperator[] OPERATORS = {
        new IntegerOperator("+", (ls, rs) -> ls + rs),
        new IntegerOperator("-", (ls, rs) -> ls - rs),
        new IntegerOperator("×", (ls, rs) -> ls * rs),
        new IntegerOperator("÷", (ls, rs) -> ls / rs)
    };

    /**
     * Prompts a random math question
     * @param scanner The scanner to use to get user input
     * @return Whether the user got the question correct
     */
    public static boolean askQuestion(ValidatedScanner scanner) {
        // Generates two random operands between 1 and 100 (exclusive)
        int ls = (int) (Math.random() * 99) + 1;
        int rs = (int) (Math.random() * 99) + 1;

        // Fetch a random operator
        IntegerOperator operator = OPERATORS[(int) (Math.random() * OPERATORS.length)];

        int answer = operator.operation.calculate(ls, rs);
        String prompt = String.format("What is %s %s %s?\n> ", ls, operator.symbol, rs);

        // Guess must be an integer
        int guess = scanner.readInput(prompt, (str) -> Integer.parseInt(str));

        // Process answer
        boolean correct = answer == guess;

        if(correct) 
            TerminalTextFormatter.println("CORRECT", ANSIFlag.GREEN_TEXT, ANSIFlag.BOLD);
        else
            TerminalTextFormatter.println("WRONG", ANSIFlag.RED_TEXT, ANSIFlag.BOLD);

        return correct;
    }
}

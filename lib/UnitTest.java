import java.util.Collection;

public class UnitTest {
    public interface Tester<T> {
        public abstract boolean test(T value);
    }

    /** 
     * Compares two values and checks whether they are equal. Prints a message if the test failed. 
     * 
     * @param <Value> The type of the value
     * @param expectedValue The expected value
     * @param receivedValue The received value
     * @param messageIfFailed The message to print if the test fails
     * */
    public static <Value> boolean test(Value expectedValue, Value receivedValue, String messageIfFailed) {
        if(!expectedValue.equals(receivedValue)) {
            TerminalTextFormatter.println(
                String.format("%s - Expected Value: '%s' | Received Value: '%s'", messageIfFailed, expectedValue, receivedValue),
                TerminalTextFormatter.ANSIFlag.YELLOW_TEXT
            );
            return false;
        }

        return true;
    }

    /**
     * Tests the provided test cases and returns whether all tests passed.
     * Tests all cases and handles exceptions if necessary.
     * @param <Unit> The type of the test case
     * @param tester A function that accepts a {@code Unit} and returns a boolean whether the unit passes
     * @param testCases An array of {@code Unit}s
     * @return Whether all test cases passed or not
     */
    public static <Unit> boolean testBatch(Tester<Unit> tester, Unit[] testCases) {
        boolean testPassed = true;

        for(Unit testCase: testCases) {
            testPassed = testPassed && tryTest(tester, testCase);
        }

        return testPassed;
    }

    /**
     * Tests the provided test cases and returns whether all tests passed.
     * Tests all cases and handles exceptions if necessary.
     * @param <Unit> The type of the test case
     * @param tester A function that accepts a {@code Unit} and returns a boolean whether the unit passes
     * @param testCases A collection of {@code Unit}s
     * @return Whether all test cases passed or not
     */
    public static <Unit> boolean testBatch(Tester<Unit> tester, Collection<Unit> testCases) {
        boolean testPassed = true;

        for(Unit testCase: testCases) {
            testPassed = testPassed && tryTest(tester, testCase);
        }

        return testPassed;
    }
    
    /**
     * Tests the provided test cases and returns whether all tests passed.
     * Tests all cases and handles exceptions if necessary.
     * @param <Unit> The type of the test case
     * @param tester A function that accepts a {@code Unit} and returns a boolean whether the unit passes
     * @param testCases An iterable that returns {@code Unit}s
     * @return Whether all test cases passed or not
     */
    public static <Unit> boolean testBatch(Tester<Unit> tester, Iterable<Unit> testCases) {
        boolean testPassed = true;

        for(Unit testCase: testCases) {
            testPassed = testPassed && tryTest(tester, testCase);
        }

        return testPassed;
    }

    private static <Unit> boolean tryTest(Tester<Unit> tester, Unit testCase) {
        try {
            if(tester.test(testCase)) return false;
        } catch (Exception e) {
            TerminalTextFormatter.println(
                String.format("Test case '%s' raised an exception:", testCase.toString()),
                TerminalTextFormatter.ANSIFlag.RED_TEXT
            );
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

package util;

public class Tester {
    /** 
     * Compares two values and checks whether they are equal. Prints a message if the test failed. 
     * 
     * @param expectedValue The expected value
     * @param receivedValue The received value
     * @param messageIfFailed The message to print if the test fails
     * */
    public static <T> boolean test(T expectedValue, T receivedValue, String messageIfFailed) {
        if(!expectedValue.equals(receivedValue)) {
            ColourFormatter.printf(
                ColourFormatter.ANSIColour.RED, 
                "%s - Expected Value: '%s' | Received Value: '%s'\n", 
                messageIfFailed, expectedValue, receivedValue
            );
            return false;
        }

        return true;
    }
}

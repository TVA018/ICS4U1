public class Tester {
    public static <T> boolean test(T expectedValue, T receivedValue, String messageIfFailed) {
        if(expectedValue != receivedValue) {
            System.out.printf("%s - Expected Value: %s | Received Value: %s\n", messageIfFailed, expectedValue, receivedValue);
            return false;
        }

        return true;
    }
}

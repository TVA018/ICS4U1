public class SecurityCheck {
    private static final String QUESTION_PROMPT = "What is this class's code?(i.e. TEJ3M1)\n> ";
    private static final ValidatedScanner.StringInputParser<Boolean> CLASS_CODE_PARSER = inputString -> inputString.toUpperCase().equals("ICS4U1");

    /**
     * Runs the security check
     * @param scanner A scanner for user input
     * @return Whether the check was passed or not
    */
    public static boolean check(ValidatedScanner scanner) {
        // Give 3 attempts
        for(int attemptsRemaining = 3; attemptsRemaining > 0; attemptsRemaining--) {
            // Exit loop and return if the check is passed
            if(scanner.readInput(QUESTION_PROMPT, CLASS_CODE_PARSER)) return true;

            // Print how many attempts the user has left
            System.out.printf("Wrong answer, you have %s attempt(s) remaining.\n", attemptsRemaining - 1);
        }

        System.out.println("Security check failed, exitting program.");
        return false;
    }
}

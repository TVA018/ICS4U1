public class SecurityCheck {
    private static final String QUESTION_PROMPT = "What is this class's code?(i.e. TEJ3M1)\n> ";
    private static final ValidatedScanner.StringInputParser<Boolean> CLASS_CODE_PARSER = inputString -> {
        if(inputString.toUpperCase().equals("ICS4U1")) return true;

        return false;
    };

    public static boolean check(ValidatedScanner scanner) {
        for(int attemptsRemaining = 3; attemptsRemaining > 0; attemptsRemaining--) {
            if(scanner.readInput(QUESTION_PROMPT, CLASS_CODE_PARSER)) return true;

            System.out.printf("Wrong answer, you have %s attempt(s) remaining.\n", attemptsRemaining - 1);
        }

        System.out.println("Security check failed, exitting program.");
        return false;
    }
}

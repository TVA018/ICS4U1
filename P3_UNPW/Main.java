import java.util.regex.Pattern;

public class Main {
    // Test cases
    private record UsernameTestCase(String firstNameInput, String lastNameInput, String expectedOutputExcludingDigits) {}
    private record PasswordTestCase(String testPassword, boolean expectedToPass) {}

    private static final UsernameTestCase[] usernameTestCases = {
        new UsernameTestCase("Alan", "Turing", "aturi"),
        new UsernameTestCase("alan", "turing", "aturi"),
        new UsernameTestCase("Ada", "Lovelace", "alove"),
        new UsernameTestCase("ada", "lovelace", "alove"),
        new UsernameTestCase("Thomas", "Vu", "tvu")
    };

    private static final PasswordTestCase[] passwordTestCases = {
        new PasswordTestCase("293819231541", false),
        new PasswordTestCase("short", false),
        new PasswordTestCase("passwords", false),
        new PasswordTestCase("PASSWORDS", false),
        new PasswordTestCase("PAssWoRDs", false),
        new PasswordTestCase("PASSword1", false),
        new PasswordTestCase("PASSword1?", true),
        new PasswordTestCase("djoiqwdioWOIDjqoiwdjoiD9823*", true),
    };

    private static final String passwordRequirementMessage = String.join("\n\t- ", 
        "Password Requirement:",
        "At least 10 characters long",
        "Include at least 1 lowercase letter",
        "Include at least 1 uppercase letter",
        "Include at least 1 digit (0-9)",
        "Include at least 1 non-alphanumeric, non-whitespace character (i.e. $)"
    );

    private static final Pattern lowercasePattern = Pattern.compile("[a-z]");
    private static final Pattern uppercasePattern = Pattern.compile("[A-Z]");
    private static final Pattern digitPattern = Pattern.compile("\\d");

    // Excludes alphanumeric characters and white space
    private static final Pattern uniqueCharactersPattern = Pattern.compile("[^a-zA-Z\\d\\s]");

    private static final ValidatedScanner.StringInputParser<String> nameValidator = inputString -> {
        if(inputString.length() == 0) throw new RuntimeException("A name must be provided.");

        return inputString.replace(" ", ""); // Remove spaces
    };

    private static final ValidatedScanner.StringInputParser<String> passwordValidator = inputString -> {
        if(inputString.length() < 10) throw new RuntimeException("The password must be at least 10 characters long");
        if(!lowercasePattern.matcher(inputString).find()) throw new RuntimeException("The password must contain at least 1 lowercase letter");
        if(!uppercasePattern.matcher(inputString).find()) throw new RuntimeException("The password must contain at least 1 uppercase letter");
        if(!digitPattern.matcher(inputString).find()) throw new RuntimeException("The password must contain at least 1 digit (0-9)");
        if(!uniqueCharactersPattern.matcher(inputString).find()) throw new RuntimeException("The password must contain at least 1 non-alphanumeric, non-whitespace character");

        return inputString;
    };
    
    private static final ValidatedScanner scanner = new ValidatedScanner();

    /**
     * Takes in a number and tries returns it in string representation while ensuring the string is at least {@code stringLength} long by padding the left with zeroes
     * @param number The input number
     * @param stringLength The minimum length of the string
     * @return The input number as a string
    */
    private static String leadWithZeroes(int number, int stringLength) {
        String numberAsString = String.valueOf(number);
        int numZeroes = stringLength - numberAsString.length();

        if(numZeroes <= 0) return numberAsString; // Can't pad anymore

        return "0".repeat(numZeroes) + numberAsString;
    }

    /**
     * Tests the {@code generateUsername()} function
     * @return Whether the test cases passed or not
    */
    private static boolean testGenerateUsername() {
        boolean testCasesPassed = true; // A flag representing whether the tests passed or not

        // Tries the test cases
        try {
            for(UsernameTestCase testCase : usernameTestCases) {
                if(
                    !Pattern.compile(testCase.expectedOutputExcludingDigits() + "\\d\\d") // Create a regex using the expected output + 2 digits
                    .matcher(generateUsername(testCase.firstNameInput(), testCase.lastNameInput())) // Test the regex against the generated username
                    .find() // Check if it matches
                ) testCasesPassed = false;
            }
        } catch (Exception e) { // This will happen if there was some kind of runtime error independent of the test cases
            e.printStackTrace();
            return testCasesPassed = false;
        }

        return testCasesPassed;
    }

    /**
     * Tests the {@code passwordValidator}
     * @return Whether the test cases passed or not
    */
    private static boolean testPasswordValidation() {
        boolean testCasesPassed = true; // A flag representing whether the tests passed or not

        // Tries all test cases
        for(PasswordTestCase testCase : passwordTestCases) {
            boolean passwordIsValid = false; // A flag representing whether the passwordValidator states the password is valid or not

            try { // Wrap in a try-except because the validator will throw a RuntimeException if the password is not valid
                passwordValidator.parseInput(testCase.testPassword());
                passwordIsValid = true;
            } catch (RuntimeException e) {} // Password isn't valid
            catch (Exception e) { // Some different error happened that was unexpected
                e.printStackTrace();
                testCasesPassed = false;
            }

            if(passwordIsValid != testCase.expectedToPass()) { // The result doesn't match the expected result
                testCasesPassed = false;
                System.out.printf("Test case for '%s' failed - Expected result: %s | Actual result: %s\n",
                    testCase.testPassword(),
                    testCase.expectedToPass() ? "Valid password" : "Invalid password",
                    passwordIsValid ? "Valid password" : "Invalid password"
                );
            }
        }

        return testCasesPassed;
    }

    /**
     * Generates a username based on the first name and last name. The username is in the format of:
     * [first name initial][up to 4 letters of last name][2 random digits]
     * The username will be in lowercase
     * @param firstName The first name
     * @param lastName The last name
     * @return The generated username
    */
    public static String generateUsername(String firstName, String lastName) {
        // Convert to lowercase to follow specification
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();

        String firstNameInitial = firstName.substring(0, 1); // First name as a letter
        String lastNameSubstring = lastName.substring(
            0, 
            Math.min(lastName.length(), 4) // Ensure the last name consists of 4 letters at most
        );
        int randomDigits = (int) (Math.random() * 99); // Random 2-digit number at most

        return firstNameInitial + lastNameSubstring + leadWithZeroes(randomDigits, 2); // Make sure randomDigits has leading zeroes if necessary
    }

    /**
     * Will prompt the user for information to generate a username
     * @return The generated username
    */
    public static String getUserInputUsername() {
        String firstName = scanner.readInput("First name: ", nameValidator);
        String lastName = scanner.readInput("Last name: ", nameValidator);

        return generateUsername(firstName, lastName);
    }

    public static void main(String[] args) {
        // Run test cases
        if(!testGenerateUsername()) {
            System.out.println("USERNAME GENERATOR TEST CASES FAILED.");
            return;
        }

        if(!testPasswordValidation()) {
            System.out.println("PASSWORD VALIDATION TEST CASES FAILED");
            return;
        }

        String username = getUserInputUsername();

        System.out.println(passwordRequirementMessage); // Let the user know the password requirements
        String password = scanner.readInput("Password: ", passwordValidator);

        System.out.printf("Here is your account info:\n\tUsername: %s\n\tPassword: %s\n", username, password);
    }
}

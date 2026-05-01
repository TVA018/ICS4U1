public final class PlateGenerator {
    private PlateGenerator() {}

    private static final String VALID_QUEBEC_FIRST_LETTERS =
        "BDEGHIJKMNOPQSUWXYZ";

    private static int getRandomDigit(int minimum) {
        int range = 10 - minimum;

        return minimum + (int) (Math.random() * range);
    }

    private static int getRandomDigit() {
        return (int) (Math.random() * 10);
    }

    private static char getRandomUppercaseLetter() {
        // Uppercase ASCII alphebetic characters range from code 65-91 (exclusive)
        int codepoint = 65 + (int) (Math.random() * 26); // Get random number from 65-91 (exclusive)
        
        return Character.toChars(codepoint)[0]; // Grab the first character because there will always be one
    }

    /**
     * Generates a new Ontario license plate string according to 
     * <a href=https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Ontario#Passenger_baseplates:~:text=345%0A123%2D45A-,1972,123%2D456%0AA12%2D345%0A123%2D45A,-1973%20to%20present>this format</a>:
     * @return the license plate string
     */
    public static String generateOntarioLicensePlate() {
        int plateType = (int) (Math.random() * 3);

        if(plateType == 0) {
            return String.format("%s%s%s-%s%s%s", 
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit()
            );
        } else if(plateType == 1) {
            return String.format("%s%s%s-%s%s%s", 
                getRandomUppercaseLetter(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit()
            );
        } else {
            return String.format("%s%s%s-%s%s%s", 
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomDigit(),
                getRandomUppercaseLetter()
            );
        }
    }

    /**
     * Generates a new Quebec license plate string according to
     * <a href=https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Quebec#1979_to_present:~:text=September%202009%C2%A0%E2%80%93%20December,%5B13%5D>this format</a>
     * @return the generated license plate
     */
    public static String generateQuebecLicensePlate() {
        char first = VALID_QUEBEC_FIRST_LETTERS.charAt((int)(Math.random() * VALID_QUEBEC_FIRST_LETTERS.length()));
        int second = getRandomDigit();
        int third = (second == 0) ? getRandomDigit(1) : getRandomDigit();

        return String.format("%s%s%s-%s%s%s", 
            first,
            second,
            third,
            getRandomUppercaseLetter(),
            getRandomUppercaseLetter(),
            getRandomUppercaseLetter()
        );
    }
}

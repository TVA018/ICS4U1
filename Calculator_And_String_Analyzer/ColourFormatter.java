public class ColourFormatter {
    /**
     * ANSI CODES FOR COLOURS
    */
    public enum ANSIColour {
        RESET("0m"),
        BLACK("30m"),
        RED("31m"),
        GREEN("32m"),
        YELLOW("33m"),
        BLUE("34m"),
        PURPLE("35m"),
        CYAN("36m"),
        WHITE("37m");

        public final String ansiCode;

        ANSIColour(String subcode) {
            this.ansiCode = "\u001B[" + subcode;
        }
    }

    /**
     * Prints a string with a given colour
     * @param colour The colour to use
     * @param str The string to print
     */
    public static void print(ANSIColour colour, String str) {
        System.out.print(colourString(colour, str));
    }

    /**
     * Prints a string and end with a new line using a given colour
     * @param colour The colour to use
     * @param str The string to print
     */
    public static void println(ANSIColour colour, String str) {
        print(colour, str + "\n");
    }


    /**
     * Prints a string using a string format with a given colour
     * @param colour The colour to use
     * @param format The string format to use
     * @param args The arguments to pass into the string format
     */
    public static void printf(ANSIColour colour, String format, Object ...args) {
        print(colour, String.format(format, args));
    }

    /**
     * Returns the string represented with the colour provided
     * @param colour The colour to use
     * @param str The string to print
     * @return The coloured string
     */
    public static String colourString(ANSIColour colour, String str) {
        return colour.ansiCode + str + ANSIColour.RESET.ansiCode;
    }
}

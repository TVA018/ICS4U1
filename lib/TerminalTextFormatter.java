public final class TerminalTextFormatter {
    private TerminalTextFormatter(){}

    /**
     * ANSI CODES FOR COLOURS
     */
    public enum ANSIFlag {
        RESET("0"),

        BOLD("1"),
        ITALIC("3"),
        UNDERLINE("4"),

        BLACK_TEXT("30"),
        GREY_TEXT("38;5;236"),
        RED_TEXT("31"),
        GREEN_TEXT("32"),
        YELLOW_TEXT("33"),
        BLUE_TEXT("34"),
        PURPLE_TEXT("35"),
        CYAN_TEXT("36"),
        WHITE_TEXT("37"),

        BLACK_BG("40"),
        GREY_BG("48;5;236"),
        RED_BG("41"),
        GREEN_BG("42"),
        YELLOW_BG("43"),
        BLUE_BG("44"),
        PURPLE_BG("45"),
        CYAN_BG("46"),
        WHITE_BG("47");

        public final String ANSI_CODE;

        ANSIFlag(String subcode) {
            this.ANSI_CODE = "\u001B[" + subcode + "m";
        }
    }

    /**
     * Prints a string using the given flags
     * @param input The input value, will be converted to a string
     * @param flags The ANSI flags/escape codes to use
     */
    public static <T> void print(T input, ANSIFlag ...flags) {
        System.out.print(applyFlags(input, flags));
    }

    /**
     * Prints a string and end with a new-line using the given flags
     * @param input The input value, will be converted to a string
     * @param flags The ANSI flags/escape codes to use
     */
    public static <T> void println(T input, ANSIFlag ...flags) {
        print(input.toString() + "\n", flags);
    }

    /**
     * Apply the ANSI flags to the string provided
     * @param input The input value, will be converted to a string
     * @param flags The ANSI flags/escape codes to use
     * @return The newly formatted string
     */
    public static <T> String applyFlags(T input, ANSIFlag ...flags) {
        StringBuilder startingFlagBuilder = new StringBuilder();

        for(ANSIFlag flag: flags) {
            startingFlagBuilder.append(flag.ANSI_CODE);
        }

        return startingFlagBuilder.toString() + input.toString() + ANSIFlag.RESET.ANSI_CODE;
    }
}

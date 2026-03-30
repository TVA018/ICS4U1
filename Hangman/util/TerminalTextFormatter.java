package util;

public final class TerminalTextFormatter {
    private TerminalTextFormatter(){}

    /**
     * ANSI CODES FOR COLOURS
     */
    public enum ANSIFlag {
        RESET("0m"),

        BOLD("1m"),
        ITALIC("3m"),
        UNDERLINE("4m"),

        BLACK_TEXT("30m"),
        RED_TEXT("31m"),
        GREEN_TEXT("32m"),
        YELLOW_TEXT("33m"),
        BLUE_TEXT("34m"),
        PURPLE_TEXT("35m"),
        CYAN_TEXT("36m"),
        WHITE_TEXT("37m"),

        BLACK_BG("40m"),
        RED_BG("41m"),
        GREEN_BG("42m"),
        YELLOW_BG("43m"),
        BLUE_BG("44m"),
        PURPLE_BG("45m"),
        CYAN_BG("46m"),
        WHITE_BG("47m");

        public final String ANSI_CODE;

        ANSIFlag(String subcode) {
            this.ANSI_CODE = "\u001B[" + subcode;
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

public class ColourFormatter {
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

    public static void print(ANSIColour colour, String str) {
        System.out.print(colourString(colour, str));
    }

    public static void println(ANSIColour colour, String str) {
        print(colour, str + "\n");
    }

    public static void printf(ANSIColour colour, String format, Object ...args) {
        print(colour, String.format(format, args));
    }

    public static String colourString(ANSIColour colour, String str) {
        return colour.ansiCode + str + ANSIColour.RESET.ansiCode;
    }
}

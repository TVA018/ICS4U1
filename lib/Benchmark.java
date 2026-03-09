public final class Benchmark {
    private Benchmark() {}

    /** Unit for Time */
    public enum TimeUnit {
        SECONDS("s", 1_000_000_000),
        MILLISECONDS("ms", 1_000_000),
        MICROSECONDS("µs", 1_000),
        NANOSECONDS("ns", 1);

        public final String symbol;
        public final int multiplier;

        private TimeUnit(String symbol, int multiplier) {
            this.symbol = symbol;
            this.multiplier = multiplier;
        }
    }

    /**
     * Times how long it takes to run the {@code target} function
     * @param target The function to run
     * @return the time taken in nanoseconds
     */
    public static long time(Runnable target) {
        long startTime = System.nanoTime();
        target.run();
        return System.nanoTime() - startTime;
    }

    /**
     * Times how long it takes to run the {@code target} function, and prints out the time taken
     * @param identifier An identifier to represent the function. Used when outputting the time elapsed
     * @param target The function to run
     * @param timeUnit The time unit to print the result as
     * @return The time taken to run the function in nanoseconds
     */
    public static long timedPrint(String identifier, Runnable target, TimeUnit timeUnit) {
        long timeElapsed = time(target);
        
        TerminalTextFormatter.println(
            String.format("Time taken to run %s: %s %s", identifier, (double) timeElapsed / timeUnit.multiplier, timeUnit.symbol), 
            TerminalTextFormatter.ANSIFlag.YELLOW_TEXT, 
            TerminalTextFormatter.ANSIFlag.ITALIC
        );

        return timeElapsed;
    }

    /** 
     * Defaults time unit to milliseconds
     * @see #timedPrint(String, Runnable, TimeUnit)
     */
    public static long timedPrint(String identifier, Runnable target) {
        return timedPrint(identifier, target, TimeUnit.MILLISECONDS);
    }
}

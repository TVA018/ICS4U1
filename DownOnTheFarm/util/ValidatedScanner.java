package util;

import java.io.InputStream;
import java.util.Scanner;

/** 
 * A wrapper around the standard Java Scanner class which can take in a parser that parses the InputStream as a string. 
 * Used for an easy way to read input from an InputStream (i.e. stdin) while attempting to parse the input until it is valid.
 * For example: Reading a floating-point input while making sure it is positive. 
 */
public class ValidatedScanner {
    /** The interface for the input parser. Used so the parser can be written as a lambda function. */
    public interface StringInputParser<T> {
        /** 
         * The parsing function. If you want to validate the input, you can throw a RuntimeException if the input is invalid.
         * 
         * For example, if you want a positive integer, you can implement the function like this:
         * 
         * <blockquote><pre>
         * StringInputParser<Integer> positiveIntegerParser = inputStr -> {
         *     int parsedValue = Integer.parseInt(inputStr);
         * 
         *     if(parsedValue > 0) {
         *         return parsedValue;
         *     } else {
         *         throw new RuntimeException("The input has to be a positive integer!");
         *     }
         * }
         * </pre></blockquote>
         * 
         * @param inputString The input read as a string
         * @return The parsed input
         */
        public abstract T parseInput(String inputString);
    }

    private boolean closed = false; // Whether the scanner is closed
    private final Scanner innerScanner;

    /** {@code source} will be passed in when creating the wrapped Scanner */
    public ValidatedScanner(InputStream source) {
        innerScanner = new Scanner(source);
    }

    /** Instantiates the wrapped Scanner using stdin by default. */
    public ValidatedScanner() {
        this(System.in);
    }

    /** Closes the inner scanner */
    public void close() {
        closed = true;
        innerScanner.close();
    }

    /** 
     * Reads a line from the input stream (i.e. terminal) and returns the parsed output. 
     * Will constantly reprompt the user until a valid input is given.
     * 
     * @param <T> The type of the parsed input
     * @param promptMessage A message to print when prompting the user
     * @param parser The parser for parsing the input. See {@link StringInputParser} for details
     * @return The parsed input 
     */
    public <T> T readInput(String promptMessage, StringInputParser<T> parser) {
        // Prevent the scanner from running when closed.
        if(closed) throw new IllegalStateException("The scanner is closed.");

        while (true) { // Will reprompt until the user enters a valid input
            System.out.print(promptMessage);
            String inputString = innerScanner.nextLine();

            try {
                // Try to parse the input
                return parser.parseInput(inputString);
            } catch(Exception e) {
                // Failed to parse, let the user know what went wrong
                System.out.printf("%s is not a valid input - %s\n", inputString, e);
            }
        }
    }

    /** 
     * Reads a line from the input stream (i.e. terminal) and returns the parsed output. 
     * Will constantly reprompt the user until a valid input is given.
     * 
     * @param <T> The type of the parsed input
     * @param parser The parser for parsing the input. See {@link StringInputParser} for details
     * @return The parsed input 
     */
    public <T> T readInput(StringInputParser<T> parser) {
        return readInput("", parser);
    }

    /** 
     * Reads a line from the input stream (i.e. terminal) as a string. 
     * 
     * @param promptMessage A message to print when prompting the user
     * @return The input as a string 
     */
    public String readLine(String promptMessage) {
        System.out.print(promptMessage);
        return innerScanner.nextLine();
    }

    /** 
     * Reads a line from the input stream (i.e. terminal) as a string. 
     * 
     * @return The input as a string 
     */
    public String readLine() {
        return readLine("");
    }
}

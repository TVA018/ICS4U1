import java.util.Arrays;
import java.util.InputMismatchException;

import util.FileIO;
import util.TerminalTextFormatter;
import util.ValidatedScanner;
import util.TerminalTextFormatter.ANSIFlag;
import util.ValidatedScanner.StringInputParser;

public class Game {
    private static final String[] WORDS_LIST;
    private static final StringInputParser<Character> LETTER_PARSER = (input) -> {
        if(input.length() == 0) throw new InputMismatchException("Please enter a letter.");

        char character = input.charAt(0);

        int charType = Character.getType(character);

        return character;
    };

    private static boolean cheatMode = false;

    private final ValidatedScanner scanner = new ValidatedScanner();

    private String chosenWord;
    private char[] guessedLetters = new char[26];
    private int numGuessesRemaining;

    // Create word list
    static {
        try(FileIO fileIO = new FileIO("res/words.txt")) {
            WORDS_LIST = fileIO.readLines();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize words list.");
        }
    }

    public void start() {
        Arrays.fill(guessedLetters, '\u0000');
        int randomWordIndex = (int) (Math.random() * WORDS_LIST.length);
        chosenWord = WORDS_LIST[randomWordIndex];
        numGuessesRemaining = 5;

        TerminalTextFormatter.println("Game started!", ANSIFlag.YELLOW_TEXT);;

        while (loop()) {} // Run game loop

        // User won
        if(numGuessesRemaining > 0) {
            TerminalTextFormatter.println("You won!", ANSIFlag.GREEN_TEXT);
        } else {
            TerminalTextFormatter.println("You lost :(", ANSIFlag.RED_TEXT);
        }
    }

    private boolean loop() {
        char letter = scanner.readInput("Enter a letter: ", LETTER_PARSER);

        return numGuessesRemaining > 0;
    }
} 
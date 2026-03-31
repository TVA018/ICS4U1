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

        if(charType != Character.LOWERCASE_LETTER && charType != Character.UPPERCASE_LETTER) throw new InputMismatchException("Please eneter a valid alphabetical letter");

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

    public static void enableCheatMode() {
        cheatMode = true;
    }

    public static void disableCheatMode() {
        cheatMode = false;
    }

    public void start() {
        Arrays.fill(guessedLetters, '\u0000');
        int randomWordIndex = (int) (Math.random() * WORDS_LIST.length);
        chosenWord = WORDS_LIST[randomWordIndex].trim(); // Remove any extraneous whitespace
        numGuessesRemaining = 5;

        TerminalTextFormatter.println("Game started!", ANSIFlag.YELLOW_TEXT);;

        while (loop()) { // Run game loop
            System.out.println();
        }

        // User won
        if(numGuessesRemaining > 0) {
            TerminalTextFormatter.println("You won!", ANSIFlag.GREEN_TEXT);
        } else {
            TerminalTextFormatter.println("You lost :(", ANSIFlag.RED_TEXT);
            TerminalTextFormatter.println("The word was '" + chosenWord + "'", ANSIFlag.BOLD);
        }
    }

    private boolean loop() {
        char letter = scanner.readInput("Enter a letter: ", LETTER_PARSER);

        if(!insertChar(guessedLetters, letter)) {
            TerminalTextFormatter.println("That letter has already been pressed.", ANSIFlag.BOLD);
        } else if(!wordContainsLetter(letter)) {
            numGuessesRemaining--;
        }

        if(parseAndPrintWordHidden()) {
            return false; // End game
        }

        System.out.println("Number of guesses remaining: " + String.valueOf(numGuessesRemaining));

        return numGuessesRemaining > 0;
    }

    private boolean insertChar(char[] characters, char letter) {
        if(characters[characters.length - 1] != '\u0000') return false;

        for(int i = 0; i < characters.length; i++) {
            if(characters[i] == letter) {
                return false;
            }

            if(characters[i] == '\u0000') {
                characters[i] = letter;
                return true;
            }
        }

        return false;
    }

    private boolean letterBeenGuessed(char letter) {
        for(char guessedLetter: guessedLetters) {
            if(guessedLetter == letter) return true;
        }

        return false;
    }

    private boolean wordContainsLetter(char letter) {
        for(int i = 0; i < chosenWord.length(); i++) {
            if(chosenWord.charAt(i) == letter) return true;
        }

        return false;
    }

    private boolean parseAndPrintWordHidden() {
        StringBuilder outputBuilder = new StringBuilder();
        boolean missedLetter = false;

        for(int i = 0; i < chosenWord.length(); i++) {
            char letter = chosenWord.charAt(i);

            if(letterBeenGuessed(letter)) {
                outputBuilder.append(letter);
            } else {
                outputBuilder.append(cheatMode ? TerminalTextFormatter.applyFlags(letter, ANSIFlag.GREY_TEXT) : "*");
                missedLetter = true;
            }
        }

        System.out.println(outputBuilder);

        return !missedLetter;
    }
} 
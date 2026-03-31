import java.util.Arrays;
import java.util.InputMismatchException;

import util.FileIO;
import util.TerminalTextFormatter;
import util.ValidatedScanner;
import util.TerminalTextFormatter.ANSIFlag;
import util.ValidatedScanner.StringInputParser;

public class Game {
    private static final String[] WORDS_LIST;

    /** Forces the input to be at least 1 letter and the first letter must be an alphabetic character. Returns the first letter as a char */
    private static final StringInputParser<Character> LETTER_PARSER = (input) -> {
        if(input.length() == 0) throw new InputMismatchException("Please enter a letter.");

        char character = input.charAt(0);

        int charType = Character.getType(character);

        if(charType != Character.LOWERCASE_LETTER && charType != Character.UPPERCASE_LETTER) throw new InputMismatchException("Please eneter a valid alphabetical letter");

        return Character.toUpperCase(character);
    };

    private final ValidatedScanner scanner = new ValidatedScanner();

    private static boolean cheatMode = false;

    private String chosenWord;

    /** Buffer for which characters were guessed. */
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

    /** Enables cheat mode */
    public static void enableCheatMode() {
        cheatMode = true;
    }

    /** Disables cheat mode */
    public static void disableCheatMode() {
        cheatMode = false;
    }

    /** Starts a new game */
    public void start() {
        Arrays.fill(guessedLetters, '\u0000'); // Reset guessed characters buffer
        int randomWordIndex = (int) (Math.random() * WORDS_LIST.length); // Index of a random word in the list

        // Remove any extraneous whitespace, and convert to uppercase
        chosenWord = WORDS_LIST[randomWordIndex].trim().toUpperCase();
        numGuessesRemaining = 5; // Start with 5 guesses

        TerminalTextFormatter.println("Game started!", ANSIFlag.YELLOW_TEXT);

        while (loop()) { // Run game loop
            System.out.println(); // Extra line just for padding
        }

        // User won
        if(numGuessesRemaining > 0) {
            TerminalTextFormatter.println("You won!", ANSIFlag.GREEN_TEXT);
        } else {
            TerminalTextFormatter.println("You lost :(", ANSIFlag.RED_TEXT);
            TerminalTextFormatter.println("The word was '" + chosenWord + "'", ANSIFlag.BOLD);
        }
    }

    /** Runs a single game loop
     * @return whether the game should continue
     */
    private boolean loop() {
        // Show guessed letters
        System.out.print("Guessed letters: ");
        for(int i = 0; i < guessedLetters.length; i++) {
            boolean isEmptySlot = guessedLetters[i] == '\u0000';
            boolean isFirstSlot = i == 0;

            if(isEmptySlot) { // Empty slot
                if(isFirstSlot) System.out.print("None");
                
                break;
            }

            if(!isFirstSlot)
                System.out.print(", "); // Add a comma

            System.out.print(guessedLetters[i]);
        }
        System.out.println();

        char letter = scanner.readInput("Enter a letter: ", LETTER_PARSER);

        // If the character has already been guessed
        if(!guessLetter(letter)) {
            TerminalTextFormatter.println("That letter has already been pressed.", ANSIFlag.BOLD);
        } else if(!wordContainsLetter(letter)) { // Wrong guess
            numGuessesRemaining--;
        }

        // Correctly guessed word
        if(processHiddenLetters()) {
            return false; // End game
        }

        System.out.println("Number of guesses remaining: " + String.valueOf(numGuessesRemaining));

        return numGuessesRemaining > 0; // Ends game if user has no guesses left
    }

    /** Inserts a letter into the guessed letter if the letter has not already been guessed
     * @return whether the letter has already been guessed
     */
    private boolean guessLetter(char letter) {
        for(int i = 0; i < guessedLetters.length; i++) {
            if(guessedLetters[i] == letter) { // Letter already guessed
                return false;
            }

            if(guessedLetters[i] == '\u0000') { // Empty slot
                guessedLetters[i] = letter;
                break;
            }
        }

        return true;
    }

    /** @return Whether the letter as already been guessed */
    private boolean letterBeenGuessed(char letter) {
        for(char guessedLetter: guessedLetters) {
            if(guessedLetter == letter) return true;
        }

        return false;
    }

    /** @return whether the chosen word contains the letter */
    private boolean wordContainsLetter(char letter) {
        for(int i = 0; i < chosenWord.length(); i++) {
            if(chosenWord.charAt(i) == letter) return true;
        }

        return false;
    }

    /** Prints the word while concealing unguessed letters
     * @return whether the full word has been guessed
     */
    private boolean processHiddenLetters() {
        StringBuilder outputBuilder = new StringBuilder();
        boolean missedLetter = false;

        for(int i = 0; i < chosenWord.length(); i++) {
            char letter = chosenWord.charAt(i);

            if(letterBeenGuessed(letter)) {
                outputBuilder.append(letter);
            } else {
                // Use substitute the letter with * in normal mode, and only grey it out instead in cheat mode
                outputBuilder.append(cheatMode ? TerminalTextFormatter.applyFlags(letter, ANSIFlag.GREY_TEXT) : "*");
                missedLetter = true;
            }
        }

        System.out.println(outputBuilder); // Print concealed word

        return !missedLetter;
    }
} 
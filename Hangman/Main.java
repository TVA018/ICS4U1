import java.util.Scanner;

import util.ValidatedScanner;

public class Main {
    public static void main(String[] args) {
        ValidatedScanner scanner = new ValidatedScanner();

        // Start game
        if(!scanner.readLine("Would you like to enable cheat mode? (Enter any letter and press enter to)\n> ")
            .isEmpty()) // If the user entered a letter
            Game.enableCheatMode();

        Game game = new Game();
        game.start(scanner);

        scanner.close();
    }
}
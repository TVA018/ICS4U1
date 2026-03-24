import util.TerminalTextFormatter;
import util.ValidatedScanner;
import util.TerminalTextFormatter.ANSIFlag;

public class Main {
    public static void main(String[] args) {
        ValidatedScanner scanner = new ValidatedScanner();

        String startingPosStr = scanner.readLine("Where would you like to start? (-25 - +25, Leave blank to start at the default\n> ");
        Ship ship;

        try {
            int startingPos = Integer.parseInt(startingPosStr);

            if(startingPos < Ship.MIN_POSITION || startingPos > Ship.MAX_POSITION)
                System.out.println("Position out of bounds. Constructor will automatically use a default value");

            ship = new Ship(startingPos);
        } catch (Exception e) {
            System.out.println("Invalid input, using default.");
            ship = new Ship();
        }

        TerminalTextFormatter.println("-->SHIP GAME<--", ANSIFlag.BOLD);
        TerminalTextFormatter.println(
            String.format("Try to reach %s by answering these questions correctly!", Ship.MAX_POSITION), 
            ANSIFlag.ITALIC
        );
        TerminalTextFormatter.println("NOTE: DIVISION QUESTIONS TRUNCATE THE DECIMALS", ANSIFlag.YELLOW_TEXT);

        System.out.println("PRESS ENTER TO CONTINUE");
        scanner.readLine();

        while (ship.getPosition() < Ship.MAX_POSITION) {
            System.out.println(ship);

            if(MathQuestionaire.askQuestion(scanner))
                ship.moveForward();
            else
                ship.moveBackward();
        }

        System.out.println(ship);
        
        TerminalTextFormatter.println("CONGRATULATIONS, YOU WIN!", ANSIFlag.BOLD, ANSIFlag.YELLOW_TEXT);

        scanner.close();
    }
}

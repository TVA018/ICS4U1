import util.TerminalTextFormatter;
import util.TerminalTextFormatter.ANSIFlag;

/** Represents a ship on water */
public class Ship {
    private static final String WAVES_EMOJI = "~";

    public static final int MIN_POSITION = -25;
    public static final int MAX_POSITION = 25;

    private int position;

    /** Creates a new ship that starts at the specified position
     * @param pos The position to start at
     */
    public Ship(int pos) {
        // Sets the position to 0 if it is out of bounds
        if(pos < MIN_POSITION || pos > MAX_POSITION) {
            pos = 0;
        }

        position = pos;
    }

    /** Creates a new ship that starts at position 0 */
    public Ship() {
        this(0);
    }

    /** Moves forward a position */
    public void moveForward() {
        position = Math.min(position + 1, MAX_POSITION);
    }

    /** Moves backward a position */
    public void moveBackward() {
        position = Math.max(position - 1, MIN_POSITION);
    }

    /** @return The position of the ship */
    public int getPosition() {
        return position;
    }

    /** @return The ship on water as an ASCII representation */
    @Override
    public String toString() {
        // The number of waves on the left side of the ship (excluding the number indicator)
        int numLeftWaves = position - MIN_POSITION - 1;
        // The number of waves on the right side of the ship (excluding the number indicator)
        int numRightWaves = MAX_POSITION - position - 1;

        // The Strings to put on the left and right side of the ship
        String leftSide;
        String rightSide;

        // Create the water on the left side of the ship
        if(numLeftWaves < 0) {
            leftSide = "";
        } else {
            leftSide = "[" + String.valueOf(MIN_POSITION) + "]" + 
                TerminalTextFormatter.applyFlags(WAVES_EMOJI.repeat(numLeftWaves), ANSIFlag.BLUE_TEXT);
        }
        
        // Create the water on the right side of the ship
        if(numRightWaves < 0) {
            rightSide = "";
        } else {
            rightSide = TerminalTextFormatter.applyFlags(WAVES_EMOJI.repeat(numRightWaves), ANSIFlag.BLUE_TEXT) + 
                "[" + String.valueOf(MAX_POSITION) + "]";
        }

        return String.format("%s<\\[%s]/>%s", 
            leftSide, 
            TerminalTextFormatter.applyFlags(position, ANSIFlag.BOLD), 
            rightSide
        );
    }
}
package animals;

public class Pig {
    private static final String pigInfoFormat = String.join("\n", 
        "Colour: %s",
        "Pen Size: %s meters squared",
        "Mud Wallowed: %s",
        "Weight: %s lbs",
        "\n"
    );

    /** The colour of the pig */
    public final String colour;

    /** The area of the pen in meters squared */
    private int penSize;
    
    /** Whether the pig wallowed in mud today */
    public final boolean wallowedInMud;

    /** The weight of the pig in pounds (lbs) */
    public final double weight;

    /**
     * Creates a new pig using the specified values
     * @param colour The colour of the pig
     * @param penSize The area of the pen in meters squared
     * @param wallowedInMud Whether the pig wallowed in mud today
     * @param weight The weight of the pig in pounds (lbs)
     */
    public Pig(String colour, int penSize, boolean wallowedInMud, double weight) {
        this.colour = colour;
        this.penSize = penSize;
        this.wallowedInMud = wallowedInMud;
        this.weight = weight;
    }

    /**
     * Creates a new pig with the default values:
     * <ul>
     * <li>Colour = Pink</li>
     * <li>Pen size = 8</li>
     * <li>Wallowed in mud = true</li>
     * <li>Weight = 500</li>
     * </ul>
     */
    public Pig() {
        this("black", 8, true, 500);
    }

    /** @return The area size of the pen in meters squared */
    public int getPenSize() {
        return penSize;
    }

    /**
     * Makes the sound of a pig
     */
    public void makeNoise() {
        System.out.println("OINK OINK OINK");
    }

    /**
     * Changes the size of the pen by the specified amount
     * @param increment The change in size in meters squared
     */
    public void upgradePenSize(int increment) {
        penSize += increment;
    }

    /**
     * Displays the information of the pig in the following format:
     * <br><br>
     * Colour: the colour of the pig<br>
     * Pen Size: The size of the pen in meters squared<br>
     * Mud Wallowed: whether the pig wallowed in mud today<br>
     * Weight: weight in pounds
     */
    public void displayPig() {
        System.out.printf(pigInfoFormat, colour, penSize, wallowedInMud, weight);
    }
}

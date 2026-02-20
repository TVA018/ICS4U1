package animals;

public class Sheep {
    private static final String sheepInfoFormat = String.join("\n", 
        "Name: %s",
        "Children: %s",
        "Sleeping: %s",
        "Fur Weight: %s lbs",
        "\n"
    );

    /** The name of the sheep */
    public final String name;

    /** The how many children the sheep has */
    public final int numChildren;
    
    /** Whether the sheep is sleeping */
    private boolean isSleeping;

    /** The weight of the sheep's fur in pounds (lbs) */
    public final double furWeight;

    /**
     * Creates a new sheep using the specified values
     * @param name The name of the sheep
     * @param numChildren The number of children sheep has
     * @param isSleeping Whether the sheep is sleeping
     * @param furWeight The weight of the sheep's fur in pounds (lbs)
     */
    public Sheep(String name, int numChildren, boolean isSleeping, double furWeight) {
        this.name = name;
        this.numChildren = numChildren;
        this.isSleeping = isSleeping;
        this.furWeight = furWeight;
    }

    /**
     * Creates a new sheep with the default values:
     * <ul>
     * <li>Name = Mary</li>
     * <li>Children = 0</li>
     * <li>Sleeping = false</li>
     * <li>Fur Weight = 30</li>
     * </ul>
     */
    public Sheep() {
        this("Mary", 0, false, 30);
    }

    /** Makes the sheep sleep */
    public void sleep() {
        isSleeping = true;
    }

    /** Makes the sheep wake up */
    public void wake() {
        isSleeping = false;
    }

    /** @return Whether the sheep is sleeping */
    public boolean getSleepingState() {
        return isSleeping;
    }

    /**
     * Makes the sound of a sheep
     */
    public void makeNoise() {
        System.out.println("BAAAAAAAAAA");
    }

    /**
     * Displays the information of the sheep in the following format:
     * <br><br>
     * Name: the name of the sheep<br>
     * Children: How many children does the sheep have<br>
     * Sleeping: whether the sheep is sleeping<br>
     * Fur Weight: the weight of the fur in pounds
     */
    public void displaysheep() {
        System.out.printf(sheepInfoFormat, name, numChildren, isSleeping, furWeight);
    }
}

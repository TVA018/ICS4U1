package animals;

public class Chicken {
    private static final String chickenInfoFormat = String.join("\n", 
        "Breed: %s",
        "Eggs Per Day: %s",
        "Rude: %s",
        "Weight: %s lbs",
        "\n"
    );

    /** The breed of the chicken */
    public final String breed;

    /** The number of eggs the chicken can lay per day */
    public final int eggLayRate;
    
    /** Whether the chicken is mean */
    public final boolean isMean;

    /** The weight of the chicken in pounds (lbs) */
    private double weight;

    /**
     * Creates a new chicken using the specified values
     * @param breed The breed of the chicken
     * @param eggLayRate The amount of eggs the chicken will lay per day
     * @param isMean Whether the chicken is mean
     * @param weight The weight of the chicken in pounds (lbs)
     */
    public Chicken(String breed, int eggLayRate, boolean isMean, double weight) {
        this.breed = breed;
        this.eggLayRate = eggLayRate;
        this.isMean = isMean;
        this.weight = weight;
    }

    /**
     * Creates a new chicken with the default values:
     * <ul>
     * <li>Breed = Rhode Island Red</li>
     * <li>Eggs laid per day = 1</li>
     * <li>Is mean = false</li>
     * <li>Weight = 6.5</li>
     * </ul>
     */
    public Chicken() {
        this("Rhode Island Red", 1, false, 6.5);
    }

    /** @return the weight of the chicken in pounds */
    public double getWeight() {
        return weight;
    }

    /**
     * Makes the sound of a chicken
     */
    public void makeNoise() {
        System.out.println("CLUCK CLUCK CLUCK");
    }

    /**
     * Changes the weight of the chicken by the specified increment
     * @param increment The change in the chicken's weight in pounds
     */
    public void eatFood(int increment) {
        weight += increment;
    }

    /**
     * Displays the information of the chicken in the following format:
     * <br><br>
     * Breed: the breed of the chicken<br>
     * Eggs Per Day: the number of eggs laid per day<br>
     * Rude: whether the chicken is mean<br>
     * Weight: weight in pounds
     */
    public void displayChicken() {
        System.out.printf(chickenInfoFormat, breed, eggLayRate, isMean, weight);
    }
}

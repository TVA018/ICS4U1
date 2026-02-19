package animals;

public class Chicken {
    private static final String chickenInfoFormat = String.join("\n", 
        "%s (Chicken)",
        "- Eggs laid per day: %s",
        "- Is mean?: %s",
        "- Weight: %s lbs",
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

    /**
     * Displays the information of the chicken in the following format:
     * <br><br>
     * Breed (Chicken):<br>
     * - Eggs laid per day: the number of eggs laid per day<br>
     * - Is mean: whether the chicken is mean<br>
     * - Weight: weight in pounds
     */
    public void displayChicken() {
        System.out.printf(chickenInfoFormat, breed, eggLayRate, isMean, weight);
    }
}

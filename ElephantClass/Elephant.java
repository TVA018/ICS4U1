/** Represents an Elephant animal */
public class Elephant extends Animal {
    private int trunkLength;

    /**
     * Constructs a new elephant with a weight of 200 and the given trunk length
     * @param trunkLength how long the trunk should be
     */
    public Elephant(int trunkLength) {
        super("Elephant", 200);

        this.trunkLength = trunkLength;
    }

    /** Increases the weight of the elephant by 20 */
    public void eatMeal() {
        setWeight(getWeight() + 20);
    }

    /** Decreases the weight of the elephant by 5 (weight cannot go below 1) */
    public void walkAround() {
        setWeight(Math.max(1, getWeight() - 5));
    }

    /** Increase the trunk length by the remainder of (weight / 4) */
    public void grow() {
        trunkLength += getWeight() % 4;
    }

    /** 
     * @return Format: weight = {@code weightValue}, trunkLength = {@code trunkLengthValue}
     */
    @Override
    public String toString() {
        return String.format("weight = %s, trunkLength = %s", getWeight(), trunkLength);
    }
}

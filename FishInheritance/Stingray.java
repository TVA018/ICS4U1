public class Stingray extends Fish {
    /** Constructs a new stingray with an age of 1 and name of "Stingray" */
    public Stingray() {
        super(1, "Stingray");
    }

    /**
     * Constructs a new stingray with the given age and name
     * @param age The age of the stingray
     * @param name The name of the stingray
     */
    public Stingray(int age, String name) {
        super(age, name);
    }

    /** Prints information on the stingray */
    @Override
    public void greeting() {
        System.out.printf("It's a stingray! Its name is %s and can sting, be careful now!\n", getName());
    }

    /** Prints the stingray stinging */
    public void sting() {
        System.out.println("Sting!");
    }
}

package animals;

public class Cow {
    private static final String cowInfoFormat = String.join("\n", 
        "%s (Cow)",
        "- Weight: %s lbs",
        "- Age: %s year(s) old",
        "- Gender: %s",
        "\n"
    );

    public final String name;

    /** The weight of the cow in pounds (lbs) */
    public final double weight;

    /** The age of the cow in years */
    private int age;

    /** The gender of the cow (?) */
    public final boolean gender;

    /**
     * Creates a new cow using the specified values
     * @param name The name of the cow
     * @param weight The weight of the cow in pounds (lbs)
     * @param age The age of the cow in year(s)
     * @param gender The gender of the cow (?)
     */
    public Cow(String name, double weight, int age, boolean gender) {
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Creates a new cow with the default values:
     * <ul>
     * <li>Name = Franny</li>
     * <li>Weight = 100</li>
     * <li>Age = 5</li>
     * <li>Gender = false</li>
     * </ul>
     */
    public Cow() {
        this("Franny", 100, 5, false);
    }

    /**
     * @return The age of the cow
     */
    public int getAge() {
        return age;
    }

    /**
     * Makes cow noises
     */
    public void makeNoise() {
        System.out.println("MOOOOOOO!");
    }

    /**
     * Increments the cow's age by 1 year
     */
    public void haveBirthday() {
        age++;
    }

    /**
     * Displays the information of the cow in the following format:
     * <br><br>
     * Name (Cow):<br>
     * - Weight: weight in pounds<br>
     * - Age: age in years<br>
     * - Gender: gender as a boolean (?)
     */
    public void displayCow() {
        System.out.printf(cowInfoFormat, name, weight, age, gender);
    }
}

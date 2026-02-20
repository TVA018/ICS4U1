import animals.Chicken;
import animals.Cow;
import animals.Pig;
import animals.Sheep;

public class Client {
    public static void main(String[] args) {
        ColourFormatter.println(ColourFormatter.ANSIColour.BLUE, "TESTING MINIMUM REQUIREMENTS");
        testMinimumRequirements();

        ColourFormatter.println(ColourFormatter.ANSIColour.YELLOW, "LEVEL UP");
        testLevelUp();
    }

    /** Tests the minimum requirements of the assignmnet */
    private static void testMinimumRequirements() {
        Cow franny = new Cow();
        Cow gretta = new Cow("Gretta", 100, 1, false);

        Chicken clucky = new Chicken();
        Chicken marshmallow = new Chicken("Sussex", 2, false, 83.5);

        Pig piglet = new Pig();
        Pig pickles = new Pig("Yellow", 10, true, 567.88);

        franny.makeNoise();
        clucky.makeNoise();
        pickles.makeNoise();

        gretta.haveBirthday();
        marshmallow.eatFood(10);
        piglet.upgradePenSize(3);

        gretta.displayCow();
        marshmallow.displayChicken();
        piglet.displayPig();
    }

    /** Tests level up portion of assignment */
    private static void testLevelUp() {
        Sheep mary = new Sheep();
        Sheep carl = new Sheep("Carl", 2, true, 54.6);

        mary.makeNoise();

        mary.displaysheep();
        carl.displaysheep();

        System.out.println("Waking Carl up");
        carl.wake();

        carl.displaysheep();
    }
}
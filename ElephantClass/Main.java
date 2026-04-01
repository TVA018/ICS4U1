import java.util.ArrayList;

/** Provided Driver code from Assignment */
public class Main {
    public static void main(String[] args) {
        ArrayList<Elephant> enclosure = new ArrayList<Elephant>();     

        enclosure.add(new Elephant(80));
        enclosure.add(new Elephant(75));
        enclosure.add(new Elephant(84));
        enclosure.get(2).eatMeal();
        enclosure.get(1).walkAround();
        enclosure.get(0).grow();
        System.out.println(enclosure.get(0).toString());
        System.out.println(enclosure.get(1).toString());
        System.out.println(enclosure.get(2).toString());
    }
}

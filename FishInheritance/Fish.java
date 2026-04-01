public class Fish {
    //fields.
    private int age;
    private String name;

    /**
     * Constructor that takes no parameter.
     */
    public Fish(){
        name = "Fish.";
        age = 1;
    }

    /**
     * Constructor.
     */
    public Fish(int age, String name){
        this.age = age;
        this.name = name;
    }

    /** @return the name of the fish */
    public String getName() {
        return name;
    }

    /**
     *overwrite this method in sub-class
     */
    public void greeting(){
        System.out.println("Hi, I am " + name + ". I can swim.");
    }

    /**
     * overwrite default toString method.
     * Print out this fish's name and age.
     */
    public String toString(){
        return "Name: " + name + " Age: " + age;
    }
}
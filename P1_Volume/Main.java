public class Main {
    public static ValidatedScanner scanner = new ValidatedScanner();
    public static boolean programEndFlag = false;
    public static final String menuPromptMessage = String.join("\n",
        "\nWhich shape would you like to calculate the shape of?",
        "1. Triangular Prism",
        "2. Cylinder",
        "3. Cone",
        "4. Square-based Pyramid",
        "5. Sphere",
        "6. Rectangular Prism",
        "7. Frustum",
        "> "
    );

    public static void menuLoop() {
        int option = scanner.readInput(
            menuPromptMessage,
            stringInput -> Integer.parseInt(stringInput), 
            value -> 1 <= value && value <= 7
        );

        switch (option) {
            case 1:
                triangularPrism();
                break;
            case 2:
                cylinder();
                break;
            default:
                break;
        }
    }

    public static void triangularPrism() {
        double baseWidth = scanner.readInput("What is the width of the base triangle: ", inputStr -> Double.parseDouble(inputStr));
        double baseHeight = scanner.readInput("What is the height of the base triangle: ", inputStr -> Double.parseDouble(inputStr));
        double prismLength = scanner.readInput("What is the length of the prism: ", inputStr -> Double.parseDouble(inputStr));

        double volume = (baseWidth * baseHeight * prismLength) / 2;
        System.out.printf("\nThe volume of the triangular prism is: %.2f\n", volume);
    }

    public static void cylinder() {
        double radius = scanner.readInput("What is the radius of the base: ", inputStr -> Double.parseDouble(inputStr));
        double height = scanner.readInput("What is the height of the cylinder: ", inputStr -> Double.parseDouble(inputStr));

        double volume = Math.PI * Math.pow(radius, 2) * height;
        System.out.printf("\nThe volume of the cylinder is: %.2f\n", volume);
    }

    public static void main(String[] args) {
        while (!programEndFlag) {
            menuLoop();
        }   
    }
}

public class Main {
    public static ValidatedScanner scanner = new ValidatedScanner();
    public static boolean programEndFlag = false;

    /** The text to print when showing the menu */
    public static final String menuPromptMessage = String.join("\n",
        "\nWhich shape would you like to calculate the shape of?",
        "1. Triangular Prism",
        "2. Cylinder",
        "3. Cone",
        "4. Rectangular-based Pyramid",
        "5. Sphere",
        "6. Rectangular Prism",
        "7. Conical Frustum",
        "8. Quit",
        "> "
    );

    /** The parser requiring the input to be a positive decimal number */
    public static final ValidatedScanner.StringInputParser<Double> positiveDoubleParser = inputStr -> {
        double value = Double.parseDouble(inputStr);

        if(value > 0)
            return value;
        else
            throw new RuntimeException(inputStr + " is not a valid positive double (decimal).");
    };

    /** The "game loop" of the program */
    public static void menuLoop() {
        // Ask the user which shape they want to calculate the volume of
        int option = scanner.readInput(
            menuPromptMessage,
            stringInput -> {
                int choice = Integer.parseInt(stringInput);
                
                if(1 <= choice && choice <= 8)
                    return choice;
                else
                    throw new RuntimeException(stringInput + " has to be an whole number between 1 and 8 (inclusive).");
            }
        );

        // Map the user choice to its corresponding function
        switch (option) {
            case 1:
                triangularPrism();
                break;
            case 2:
                cylinder();
                break;
            case 3:
                cone();
                break;
            case 4:
                rectBasedPyramid();
                break;
            case 5:
                sphere();
                break;
            case 6:
                rectangularPrism();
                break;
            case 7:
                conicalFrustum();
                break;
            case 8: // Stops the game loop
                programEndFlag = true;
                break;
        }
    }

    /** Prompts the user for the dimensions of the triangular prism and prints the volume */
    public static void triangularPrism() {
        double baseWidth = scanner.readInput("What is the width of the base triangle: ", positiveDoubleParser);
        double baseHeight = scanner.readInput("What is the height of the base triangle: ", positiveDoubleParser);
        double prismLength = scanner.readInput("What is the length of the prism: ", positiveDoubleParser);

        double volume = (baseWidth * baseHeight * prismLength) / 2.0;
        System.out.printf("\nThe volume of the triangular prism is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the cylinder and prints the volume */
    public static void cylinder() {
        double radius = scanner.readInput("What is the radius of the base: ", positiveDoubleParser);
        double height = scanner.readInput("What is the height of the cylinder: ", positiveDoubleParser);

        double volume = Math.PI * Math.pow(radius, 2) * height;
        System.out.printf("\nThe volume of the cylinder is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the cone and prints the volume */
    public static void cone() {
        double radius = scanner.readInput("What is the radius of the base: ", positiveDoubleParser);
        double height = scanner.readInput("What is the height of the cone: ", positiveDoubleParser);

        double volume = Math.PI * Math.pow(radius, 2) * height / 3.0;
        System.out.printf("\nThe volume of the cone is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the rectangular-based pyramid and prints the volume */
    public static void rectBasedPyramid() {
        double baseLength = scanner.readInput("What is the length of the base: ", positiveDoubleParser);
        double baseWidth = scanner.readInput("What is the width of the base: ", positiveDoubleParser);
        double height = scanner.readInput("What is the height of the pyramid: ", positiveDoubleParser);

        double volume = baseLength * baseWidth * height / 3.0;
        System.out.printf("\nThe volume of the rectangular-based pyramid is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the sphere and prints the volume */
    public static void sphere() {
        double radius = scanner.readInput("What is the radius of the sphere: ", positiveDoubleParser);
        double volume = (4.0/3.0) * Math.PI * Math.pow(radius, 3);

        System.out.printf("\nThe volume of the sphere is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the rectangular prism and prints the volume */
    public static void rectangularPrism() {
        double length = scanner.readInput("What is the length of the prism: ", positiveDoubleParser);
        double width = scanner.readInput("What is the width of the prism: ", positiveDoubleParser);
        double height = scanner.readInput("What is the height of the prism: ", positiveDoubleParser);

        double volume = length * width * height;
        System.out.printf("\nThe volume of the rectangular prism is: %.2f\n", volume);
    }

    /** Prompts the user for the dimensions of the conical frustum and prints the volume */
    public static void conicalFrustum() {
        double baseRadius = scanner.readInput("What is the radius of the base (bottom face): ", positiveDoubleParser);
        double topRadius = scanner.readInput("What is the radius of the top face: ", positiveDoubleParser);
        double height = scanner.readInput("What is the height of the frustum: ", positiveDoubleParser);

        // The formula of a conical frustum was found here: https://en.wikipedia.org/wiki/Frustum#:~:text=The%20volume%20of%20a%20circular%20cone%20frustum%20is
        double volume = (Math.PI * height / 3.0) * (Math.pow(baseRadius, 2) + baseRadius * topRadius + Math.pow(topRadius, 2));

        System.out.printf("\nThe volume of the frustum is: %.2f\n", volume);
    }

    public static void main(String[] args) {
        System.out.printf("--[SHAPE VOLUME CALCULATOR]--\nPress Enter to continue...");
        scanner.readLine(); // Wait until the user enters a line

        // Runs the menu loop until the user quits the program via the menu
        while (!programEndFlag) {
            menuLoop();
        }

        scanner.close(); // Close the scanner. Not really necessary since the program ends immediately but just for good practice
        System.out.println("See you next time!");
    }
}

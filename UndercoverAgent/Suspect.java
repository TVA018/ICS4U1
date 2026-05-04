import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Represents a suspect
 * @param name The name of the subject
 * @param yearOfBirth The year of the suspect's birth
 * @param city The city the suspect lives in
 * @param hasActiveWarrant Whether the suspect has an active warrant
 * @param carBrand The brand of the suspect's car
 * @param makeYear The year the car was made
 * @param licensePlate The license plate of the car
 */
public record Suspect(
    String name, 
    int yearOfBirth, 
    String city, 
    boolean hasActiveWarrant, 
    String carBrand, 
    int makeYear,
    String licensePlate
) {
    /** The string format for English display */
    private static final String regularStringFormat = String.join("\n",
        "%s:",
        "- Year of Birth: %s",
        "- City: %s",
        "- Has Active Warrant: %s",
        "- Car Brand: %s",
        "- Make Year: %s",
        "- License Plate ID: %s"
    );
    
    /** The string format for French display */
    private static final String frenchStringFormat = String.join("\n",
        "%s:",
        "- Année de Naissance: %s",
        "- Ville: %s",
        "- Mandat Actif: %s",
        "- Marque Automobile: %s",
        "- Faire l'Année: %s",
        "- Plaque d'Immatriculation: %s"
    );

    /** The list of generated license plates */
    private static final ArrayList<String> generatedLicensePlates = new ArrayList<>();

    public Suspect {
        if(generatedLicensePlates.contains(licensePlate)) 
            throw new IllegalArgumentException(licensePlate + " is already in use!");
    }

    
    /**
     * Represents a suspect. Automatically generates a license plate
     * @param name The name of the subject
     * @param yearOfBirth The year of the suspect's birth
     * @param city The city the suspect lives in
     * @param hasActiveWarrant Whether the suspect has an active warrant
     * @param carBrand The brand of the suspect's car
     * @param makeYear The year the car was made
     */
    public Suspect(
        String name, 
        int yearOfBirth, 
        String city, 
        boolean hasActiveWarrant, 
        String carBrand, 
        int makeYear
    ) {
        Supplier<String> licensePlateSupplier = 
            (city.equalsIgnoreCase("montreal")) ? 
            PlateGenerator::generateQuebecLicensePlate :  // Use Quebec license plate format
            PlateGenerator::generateOntarioLicensePlate; // Use Ontario license plate format
        
        String generatedLicensePlate = licensePlateSupplier.get();

        // Keep re-generating license plates if this license plate is already in use
        while (generatedLicensePlates.contains(generatedLicensePlate)) {
            generatedLicensePlate = licensePlateSupplier.get();
        }

        // Call full constructor
        this(
            name,
            yearOfBirth,
            city,
            hasActiveWarrant,
            carBrand,
            makeYear,
            generatedLicensePlate
        );
    }

    /**
     * Represents a suspect. Created from a row of a CSV file with suspect info
     * @param csvRow
     */
    public Suspect(String csvRow) {
        String[] cells = csvRow.split(",");

        this(
            cells[0],
            Integer.parseInt(cells[1]),
            cells[2],
            cells[3].equalsIgnoreCase("T"),
            cells[4],
            Integer.parseInt(cells[5])
        );
    }

    @Override
    public final String toString() {
        boolean isFrench = city.equalsIgnoreCase("montreal");
        String stringFormat = isFrench ? // Choose string format depending on city
            frenchStringFormat : 
            regularStringFormat;

        String warrantString = isFrench ?
            (hasActiveWarrant ? "Vrai" : "Faux") : // French True/False
            (hasActiveWarrant ? "True" : "False"); // English True/False

        return String.format(
            stringFormat,
            name,
            yearOfBirth,
            city,
            warrantString,
            carBrand,
            makeYear,
            licensePlate
        );
    }
}
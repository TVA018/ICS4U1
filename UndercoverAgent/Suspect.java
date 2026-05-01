import java.util.ArrayList;
import java.util.function.Supplier;

public record Suspect(
    String name, 
    int yearOfBirth, 
    String city, 
    boolean hasActiveWarrant, 
    String carBrand, 
    int makeYear,
    String licensePlate
) {
    private static final ArrayList<String> generatedLicensePlates = new ArrayList<>();

    public Suspect {
        if(generatedLicensePlates.contains(licensePlate)) 
            throw new IllegalArgumentException(licensePlate + " is already in use!");
    }

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
            PlateGenerator::generateQuebecLicensePlate : 
            PlateGenerator::generateOntarioLicensePlate;
        
        String generatedLicensePlate = licensePlateSupplier.get();

        while (generatedLicensePlates.contains(generatedLicensePlate)) {
            generatedLicensePlate = licensePlateSupplier.get();
        }

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
}
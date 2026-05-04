import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class UndercoverAgent {
    /**
     * Represents the config of an a table column
     * @param columnName The name of the column. This will be used in the header row (first row)
     * @param valueGetter A function which takes in a suspect, and returns some string from them
     */
    private final record TableColumnConfig(String columnName, Function<Suspect, String> valueGetter) {}

    /** The current year. Used for some calculations */
    private static final int currentYear = 2026;

    /** All the suspects */
    private static final ArrayList<Suspect> suspects = new ArrayList<>();
    /** The table column configs which contains all of the base variables (i.e. name, city, etc.) */
    private static final List<TableColumnConfig> allColumnsConfig = new ArrayList<>();
    /** The table column configs for Inspect Big-O */
    private static final List<TableColumnConfig> bigOColumnConfigs;

    /** Commonly used getter for the name of a suspect */
    private static final Function<Suspect, String> nameGetter = suspect -> suspect.name();

    static {
        allColumnsConfig.add(new TableColumnConfig("Name", nameGetter));
        allColumnsConfig.add(new TableColumnConfig("DOB", suspect -> String.valueOf(suspect.yearOfBirth())));
        allColumnsConfig.add(new TableColumnConfig("City", suspect -> suspect.city()));
        allColumnsConfig.add(new TableColumnConfig("Has Active Warrant", suspect -> suspect.hasActiveWarrant() ? "T" : "F"));
        allColumnsConfig.add(new TableColumnConfig("Car", suspect -> suspect.carBrand()));
        allColumnsConfig.add(new TableColumnConfig("Make Year", suspect -> String.valueOf(suspect.makeYear())));
        allColumnsConfig.add(new TableColumnConfig("Plate Id", suspect -> suspect.licensePlate()));

        // Use all the base column configs, but also add one for the car age
        bigOColumnConfigs = new ArrayList<>(allColumnsConfig);
        bigOColumnConfigs
                .add(new TableColumnConfig("Car Age (y)", suspect -> String.valueOf(currentYear - suspect.makeYear())));
    }

    public static void main(String[] args) {
        parseSuspects();
        sortSuspects();

        System.out.println("All suspects:");
        System.out.println(OutputFormatter.generateTable(getSuspectsAsCSV(suspects)));

        inspectorHopper();
        inspectorBoolean();
        inspectorBigO();

        System.out.println("Inspector Binary for Christian and Scott:");
        inspectorBinary("Christian");
        inspectorBinary("Scott");

        inspectorNull();
        inspectorShadow();
        inspectorCaesar(6);
    }

    /** Parses res/data.csv to populate the suspects list */
    private static void parseSuspects() {
        String dataFilePath = "res/data.csv";

        // Read csv file
        try (BufferedReader fileReader = new BufferedReader(new FileReader(dataFilePath))) {
            fileReader.readLine(); // Skip the first line

            for (String csvRow : fileReader.readAllLines()) {
                suspects.add(new Suspect(csvRow));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to start program");
        }
    }

    /** Sorts all of the suspects in the suspects list using selection sort */
    private static void sortSuspects() {
        for (int i = 0; i < suspects.size(); i++) {
            int indexOfSmallest = i;

            for (int j = i + 1; j < suspects.size(); j++) {
                // If the name at index j is less alphabetically than the name at
                // indexOfSmallest
                if (suspects.get(j).name().compareTo(suspects.get(indexOfSmallest).name()) < 0) {
                    indexOfSmallest = j;
                }
            }

            // If the name at index i is not the smallest, swap
            if (indexOfSmallest != i) {
                Suspect temp = suspects.get(i);
                suspects.set(i, suspects.get(indexOfSmallest));
                suspects.set(indexOfSmallest, temp);
            }
        }
    }

    /** 
     * Generates a table represented by a 2-dimensional String array from the provided suspects.
     * @param suspects The suspects to use for the table
     * @param columnConfigs The configs for the columns. This is used to determine the layout of the table
     * @return The table as a 2-dimensional String array
     */
    private static String[][] getSuspectsAsTable(List<Suspect> suspects, List<TableColumnConfig> columnConfigs) {
        String[][] table = new String[suspects.size() + 1][columnConfigs.size()];
        String[] columnNames = table[0]; // Header row

        // Set the cells of the header row
        for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            columnNames[columnIndex] = columnConfigs.get(columnIndex).columnName();
        }

        // Populate the following rows with the necessary values based off columnConfigs
        for (int suspectIndex = 0; suspectIndex < suspects.size(); suspectIndex++) {
            Suspect suspect = suspects.get(suspectIndex);
            String[] row = table[suspectIndex + 1];

            for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
                row[columnIndex] = columnConfigs.get(columnIndex).valueGetter().apply(suspect);
            }
        }

        return table;
    }

    /**
     * Generates a table represented by a 2-dimensional String array from the provided suspects. Uses the default column configs
     * @param suspects The suspects to use for the table
     * @return The table as a 2-dimensional String array
     */
    private static String[][] getSuspectsAsCSV(List<Suspect> suspects) {
        return getSuspectsAsTable(suspects, allColumnsConfig);
    }

    /**
     * Filters the suspects list by the provided predicate
     * @param validSuspectPredicate Filter out all suspects which do not pass this predicate
     * @return The filtered suspects
     */
    private static List<Suspect> filterSuspects(Predicate<Suspect> validSuspectPredicate) {
        ArrayList<Suspect> validSuspects = new ArrayList<>();

        for (Suspect suspect : suspects) {
            if (validSuspectPredicate.test(suspect))
                validSuspects.add(suspect);
        }

        return validSuspects;
    }

    /**
     * Applies a caesar cipher on a String. This only works for alphabetic characters.
     * @param original The original string
     * @param numShifts How much to shift it by
     * @return The caesar cipher-applied string
     */
    private static String caesarCipher(String original, int numShifts) {
        int alphabetRange = 26;
        int stringLength = original.length();
        StringBuilder caesarCipherShifted = new StringBuilder(stringLength);

        if (numShifts < 0) {
            numShifts = (numShifts % alphabetRange) + alphabetRange;
        }

        for (int charIndex = 0; charIndex < stringLength; charIndex++) {
            int originalCodepoint = original.charAt(charIndex);
            int shiftedCodepoint;

            // Uppercase characters
            if (65 <= originalCodepoint && originalCodepoint <= 90) {
                shiftedCodepoint = (65 + ((originalCodepoint - 65 + numShifts) % alphabetRange));
                // Lowercase characters
            } else if (97 <= originalCodepoint && originalCodepoint <= 122) {
                shiftedCodepoint = (97 + ((originalCodepoint - 97 + numShifts) % alphabetRange));
            } else {
                shiftedCodepoint = originalCodepoint;
            }

            caesarCipherShifted.append((char) shiftedCodepoint);
        }

        return caesarCipherShifted.toString();
    }

    /**
     * Prints all of the suspects who drives a Ford and has an active warrant
     */
    private static void inspectorHopper() {
        List<Suspect> validSuspects = filterSuspects(
                suspect -> suspect.hasActiveWarrant() && suspect.carBrand().equalsIgnoreCase("Ford"));

        System.out.println("Inspector Hopper:");
        System.out.println(OutputFormatter.generateTable(getSuspectsAsCSV(validSuspects)));
    }

    /**
     * Prints all of the suspects who lives in Ottawa or Kingston
     */
    private static void inspectorBoolean() {
        List<Suspect> validSuspects = filterSuspects(suspect -> {
            String city = suspect.city();

            return city.equalsIgnoreCase("Ottawa") || city.equalsIgnoreCase("Kingston");
        });

        System.out.println("Inspector Boolean:");
        System.out.println(OutputFormatter.generateTable(getSuspectsAsCSV(validSuspects)));
    }

    /**
     * Prints all of the suspects who has a car that was made between 2010 and 2013 (inclusive).
     * Also displays the age of the car
     */
    private static void inspectorBigO() {
        List<Suspect> validSuspects = filterSuspects(suspect -> {
            int makeYear = suspect.makeYear();

            return 2010 <= makeYear && makeYear <= 2013;
        });

        System.out.println("Inspector Big-O");
        System.out.println(OutputFormatter.generateTable(getSuspectsAsTable(validSuspects, bigOColumnConfigs)));
    }

    /**
     * Finds a suspect by their name via a binary search and prints their data.
     * @param suspectName The name of the suspect
     */
    private static void inspectorBinary(String suspectName) {
        int upperBound = suspects.size();
        int i = upperBound / 2;

        Suspect suspect = suspects.get(i);
        int currentDiff = suspect.name().compareTo(suspectName);

        while (currentDiff != 0) {
            if (currentDiff > 0) {
                upperBound = i;
                i /= 2;
            } else {
                i += 1 + (upperBound - i) / 2;
            }

            suspect = suspects.get(i);
            currentDiff = suspect.name().compareTo(suspectName);
        }

        System.out.println(suspect);
        System.out.println();
    }

    /**
     * Prints the city with the highest average (mean) year a car was made.
     */
    private static void inspectorNull() {
        HashMap<String, Integer> cityToVehicleYearTotal = new HashMap<>();
        HashMap<String, Integer> cityToNumSuspects = new HashMap<>();

        for (Suspect suspect : suspects) {
            String city = suspect.city();
            cityToVehicleYearTotal.put(
                    city,
                    cityToVehicleYearTotal.getOrDefault(city, 0) + suspect.makeYear());

            cityToNumSuspects.put(
                    city,
                    cityToNumSuspects.getOrDefault(city, 0) + 1);
        }

        String highestAverageCity = "";
        double highestAverageYear = -1.0;

        for (String city : cityToVehicleYearTotal.keySet()) {
            double averageVehicleYear = (double) cityToVehicleYearTotal.get(city) / cityToNumSuspects.get(city);

            if (averageVehicleYear > highestAverageYear) {
                highestAverageCity = city;
                highestAverageYear = averageVehicleYear;
            }
        }

        System.out.printf(
                "Inspector Null:\nThe city with the highest mean vehicle make year is %s (year %s)\n\n",
                highestAverageCity,
                Math.round(highestAverageYear));
    }

    /**
     * Prints all of the suspects who lives in Toronto or Montreal, 
     * and doesn't have the first letter of their name in their license plate.
     */
    private static void inspectorShadow() {
        List<Suspect> validSuspects = filterSuspects(suspect -> {
            String city = suspect.city();

            return (city.equalsIgnoreCase("toronto") || city.equalsIgnoreCase("montreal")) &&
                    !suspect.licensePlate().contains(suspect.name().substring(0, 1));
        });

        System.out.println("Inspector Shadow:");
        System.out.println(
                (validSuspects.size() > 0) ? OutputFormatter.generateTable(getSuspectsAsCSV(validSuspects))
                        : "No valid suspects found.");
    }

    /**
     * Prints all of the suspects and their name shifted by a specific amount along the alphabet via a Caesar Cipher.
     * @param numShifts How much their name should be shifted by
     */
    private static void inspectorCaesar(int numShifts) {
        ArrayList<TableColumnConfig> columnConfigs = new ArrayList<>();

        columnConfigs.add(new TableColumnConfig("Name", suspect -> suspect.name()));
        columnConfigs.add(new TableColumnConfig("Caesar Shifted " + String.valueOf(numShifts),
                suspect -> caesarCipher(suspect.name(), numShifts)));

        System.out.println("Inspector Caesar:");
        System.out.println(OutputFormatter.generateTable(getSuspectsAsTable(suspects, columnConfigs)));
    }
}

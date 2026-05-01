import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UndercoverAgent {
    private static final ArrayList<Suspect> suspects = new ArrayList<>();
    public static void main(String[] args) {
        parseSuspects();
        sortSuspects();

        for(Suspect suspect: suspects) {
            System.out.println(suspect.name());
        }
    }

    private static void parseSuspects() {
        String dataFilePath = "res/data.csv";

        // Read csv file
        try (BufferedReader fileReader = new BufferedReader(new FileReader(dataFilePath))) {
            fileReader.readLine(); // Skip the first line

            for(String csvRow : fileReader.readAllLines()) {
                suspects.add(new Suspect(csvRow));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Failed to start program");
        }
    }

    private static void sortSuspects() {
        for(int i = 0; i < suspects.size(); i++) {
            int indexOfSmallest = i;

            for(int j = i + 1; j < suspects.size(); j++) {
                // If the name at index j is less alphabetically than the name at indexOfSmallest
                if(suspects.get(j).name().compareTo(suspects.get(indexOfSmallest).name()) < 0) {
                    indexOfSmallest = j;
                }
            }

            // If the name at index i is not the smallest, swap
            if(indexOfSmallest != i) {
                Suspect temp = suspects.get(i);
                suspects.set(i, suspects.get(indexOfSmallest));
                suspects.set(indexOfSmallest, temp);
            }
        }
    }
}

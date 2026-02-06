import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeroVotingManager {
    /** Represents a ranking spot in the contest */
    private static class Ranking {
        /** The rankings are indexed from 0 */
        public final int RANK_INDEX;

        /** How many votes are required to be within this ranking */
        public final int NUM_VOTES;

        /** The heroes occupying this ranking */
        public final List<String> heroes = new ArrayList<>();

        public Ranking(int rankIndex, int numVotes) {
            this.RANK_INDEX = rankIndex;
            this.NUM_VOTES = numVotes;
        }

        @Override
        public String toString() {
            return String.format(
                "Rank %s (%s votes): %s",
                RANK_INDEX + 1,
                NUM_VOTES,
                String.join(", ", heroes)
            );
        }
    }

    /** The heroes in the contest */
    public static final String[] SUPERHERO_NAMES = {
        titleCase("iron man"), 
        titleCase("spiderman"), 
        titleCase("captain america")
    };

    /** The text to display for the menu loop */
    public static final String MENU_TEXT = String.join("\n",
        "\n--> Superhero Contest! <--",
        "1. Heroes List",
        "2. Randomized Voting",
        "3. Rigged Voting",
        "4. Quit",
        "\n> "
    );

    private final ValidatedScanner scanner;

    /** The parser/validator for the menu loop */
    private final ValidatedScanner.StringInputParser<Integer> menuChoiceParser = inputString -> {
        int value = Integer.parseInt(inputString);

        if(value < 1 || value > 4) throw new RuntimeException("The integer has to be between 1 and 4 (inclusive)");

        return value;
    };

    private final ValidatedScanner.StringInputParser<Integer> wholeNumberParser = inputString -> {
        int value = Integer.parseInt(inputString);

        if(value < 0) throw new RuntimeException("Input must be a positive integer or 0!");

        return value;
    };

    /** RegEx for the secret phrase to rig the contest */
    private final Pattern secretPhrasePattern = Pattern.compile("(?<=i am).+", Pattern.CASE_INSENSITIVE);

    public HeroVotingManager(ValidatedScanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Capitalizes the starting letter of every word in the input string
     * @param targetString The input string
     * @return The string in title case
    */
    private static String titleCase(String targetString) {
        return String.join(" ", Arrays.asList(
            targetString.split(" ")).stream() // Split into an array of words delimiting by space
            .map(word -> { // Map each word
                if(word.length() == 0) return ""; // Edge case for empty string
                if(word.length() == 1) return word.toUpperCase(); // Edge case for 1 letter word


                return 
                    word.substring(0, 1).toUpperCase() + // Capitalize the first letter
                    word.substring(1).toLowerCase(); // Return the following letters in lowercase 
            })
            .toArray(String[]::new) // Convert back into a String array so it can be concatenated
        );
    }

    /** @return a random index between 0 and the length of the superheroes array (exclusive) */
    private int getRandomSuperheroIndex() {
        return (int) (Math.random() * SUPERHERO_NAMES.length);
    }

    /**
     * Picks a random element from a list and removes it
     * @param <T> Any non-primitive type (wrappers around primitives works such as {@code Integer})
     * @param list The list
     * @return The removed element
    */
    private <T> T removeRandom(List<T> list) {
        int index = (int)(list.size() * Math.random()); // Pick random index
        return list.remove(index);
    }

    /**
     * Take in an input string and returns the superhero name to rig the contest with.
     * 
     * The input string must be in the format of "i am SUPERHERO_NAME" where SUPERHERO_NAME is a valid name (case-insensitive)
     * @param secretPhrase The input string
     * @return The superhero name in title case
    */
    private Optional<String> parseSecretPhrase(String secretPhrase) {
        // Create a Matcher
        Matcher phraseMatcher = secretPhrasePattern.matcher(secretPhrase);
        boolean matches = phraseMatcher.find();
        
        if(matches) { // Does it match the format properly
            // Trim the matched phrase because leading/trailing whitespaces shouldn't be used
            String titleCasedMatch = titleCase(phraseMatcher.group().trim());

            for(String name: SUPERHERO_NAMES) {
                // If the input name is an actual valid name
                if(titleCasedMatch.equals(name)) return Optional.of(name);
            }
        }

        // If all checks failed then the phrase was not valid
        return Optional.empty();
    }

    /** Prints the superhero names list */
    public void printHeroesList(){
        System.out.println(String.join(", ", SUPERHERO_NAMES));
    }

    private Integer[] getRandomlyDistributedVotes() {
        int numVoters = scanner.readInput("How many people are voting?\n> ", wholeNumberParser);

        // This list stores the distributed votes. Each element doesn't 
        // correspond to a specific hero, it will be randomly assigned later
        Integer[] votingDistribution = new Integer[SUPERHERO_NAMES.length];

        // Make sure the array starts with 0
        Arrays.fill(votingDistribution, 0);

        // Loop through the number of voters and randomly distribute it
        for(int i = 0; i < numVoters; i++) {
            votingDistribution[getRandomSuperheroIndex()]++;
        }

        // Sort the distribution from greatest to least
        Arrays.sort(votingDistribution, (a, b) -> b - a);

        return votingDistribution;
    }

    /** Runs a voting simulation where votes are completely random */
    public void randomizedVoting() {
        Integer[] votingDistribution = getRandomlyDistributedVotes();

        // Set up the hero pool. This will be used to randomly pop hero names
        List<String> superheroesPool = new ArrayList<>(Arrays.asList(SUPERHERO_NAMES));

        // The current rank within the iteration (for the following for loop)
        int currentRank = 0;

        // The number of votes for the current rank (for the following for loop)
        int rankVotes = votingDistribution[0];

        // The rankings for the simulation
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(currentRank, rankVotes)); // Create the initial ranking

        for (int numVotes: votingDistribution) { // Loop through the distributed votes
            String randomHero = removeRandom(superheroesPool); // Pick a random hero to assign the votes to

            if(numVotes == rankVotes) { // If the distributed votes matches the current ranking (tie)
                rankings.get(currentRank).heroes.add(randomHero); // Add this hero to the current ranking placement
            } else { // Less votes, can't be more because the votingDistribution array is sorted
                currentRank++; // Increment the rank ("lower" rank)
                rankVotes = numVotes; // Update the number of votes required to match this rank
                Ranking newRanking = new Ranking(currentRank, rankVotes); // Create a new ranking object
                newRanking.heroes.add(randomHero); // Add the hero to the new rank
                rankings.add(newRanking); // Add ranking to list
            }
        }


        // Print formatted ranking
        System.out.println(String.join("\n", 
            rankings.stream()
            .map(ranking -> ranking.toString()) // Map to String representation
            .toArray(String[]::new) // Collect into a String[] array
        ));
    }

    public void riggedVoting() {
        String secretPhrase = scanner.readLine("Enter the secret phrase: ").trim();
        Optional<String> riggedHeroOptional = parseSecretPhrase(secretPhrase);

        if(!riggedHeroOptional.isPresent()) {
            System.out.println("An invalid phrase was entered");
            return;
        }

        String riggedHero = riggedHeroOptional.get();

        int numVoters = scanner.readInput("How many people are voting?\n> ", wholeNumberParser);

        // This list stores the distributed votes. Each element doesn't 
        // correspond to a specific hero, it will be randomly assigned later
        Integer[] votingDistribution = new Integer[SUPERHERO_NAMES.length];

        // Make sure the array starts with 0
        Arrays.fill(votingDistribution, 0);

        // Loop through the number of voters and randomly distribute it
        for(int i = 0; i < numVoters; i++) {
            votingDistribution[getRandomSuperheroIndex()]++;
        }

        // Sort the distribution from greatest to least
        Arrays.sort(votingDistribution, (a, b) -> b - a); 

        // Set up the hero pool. This will be used to randomly pop hero names
        List<String> superheroesPool = new ArrayList<>(Arrays.asList(SUPERHERO_NAMES));

        // The current rank within the iteration (for the following for loop)
        int currentRank = 0;

        // The number of votes for the current rank (for the following for loop)
        int rankVotes = votingDistribution[0];

        // The rankings for the simulation
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(currentRank, rankVotes)); // Add new rank to list
        rankings.get(currentRank).heroes.add(riggedHero); // Add the rigged hero to the top rank
        superheroesPool.remove(riggedHero); // Remove the rigged hero from the pool so they won't be picked

        // Randomly distribute the rest of the heroes, see randomizedVoting() for explanation
        for (int i = 1; i < votingDistribution.length; i++) {
            int numVotes = votingDistribution[i];
            String randomHero = removeRandom(superheroesPool);

            if(numVotes == rankVotes) {
                rankings.get(currentRank).heroes.add(randomHero);
            } else {
                currentRank++;
                rankVotes = numVotes;
                Ranking newRanking = new Ranking(currentRank, rankVotes);
                newRanking.heroes.add(randomHero);
                rankings.add(newRanking);
            }
        }

        // Print formatted ranking
        System.out.println(String.join("\n", 
            rankings.stream()
            .map(ranking -> ranking.toString()) // Map to String representation
            .toArray(String[]::new) // Collect into a String[] array
        ));
    }

    /**
     * Prints the menu TUI and runs the corresponding function for each option
     * @return whether the loop should continue or not
    */
    public boolean menuLoop() {
        int choice = scanner.readInput(MENU_TEXT, menuChoiceParser);

        switch (choice) {
            case 1:
                printHeroesList();
                break;
            case 2:
                randomizedVoting();
                break;
            case 3:
                riggedVoting();
                break;
            case 4:
                return false;
        }

        return true;
    }
}

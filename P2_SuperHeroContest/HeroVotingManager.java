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
        return String.join(" ", Arrays.asList(targetString.split(" ")).stream().map(word -> {
            if(word.length() == 0) return "";
            if(word.length() == 1) return word.toUpperCase();

            return word.substring(0, 1).toUpperCase() + word.substring(1);
        }).toArray(String[]::new));
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
        int index = (int)(list.size() * Math.random());
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
        Matcher phraseMatcher = secretPhrasePattern.matcher(secretPhrase);
        boolean matches = phraseMatcher.find();
        
        if(matches) {
            String titleCasedMatch = titleCase(phraseMatcher.group().trim());
            System.out.println(titleCasedMatch);

            for(String name: SUPERHERO_NAMES) {
                if(titleCasedMatch.equals(name)) return Optional.of(name);
            }
        }

        return Optional.empty();
    }

    
    public void printHeroesList(){
        System.out.println(String.join(", ", SUPERHERO_NAMES));
    }

    public void randomizedVoting() {
        int numVoters = scanner.readInput("How many people are voting?\n> ", wholeNumberParser);

        Integer[] votingDistribution = new Integer[SUPERHERO_NAMES.length];
        Arrays.fill(votingDistribution, 0);

        for(int i = 0; i < numVoters; i++) {
            votingDistribution[getRandomSuperheroIndex()]++;
        }

        Arrays.sort(votingDistribution, (a, b) -> b - a);

        List<String> superheroesPool = new ArrayList<>(Arrays.asList(SUPERHERO_NAMES));
        int currentRank = 0;
        int rankVotes = votingDistribution[0];
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(currentRank, rankVotes));

        for (int numVotes: votingDistribution) {
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

        System.out.println(String.join("\n", rankings.stream().map(ranking -> ranking.toString()).toArray(String[]::new)));
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

        Integer[] votingDistribution = new Integer[SUPERHERO_NAMES.length];
        Arrays.fill(votingDistribution, 0);

        for(int i = 0; i < numVoters; i++) {
            votingDistribution[getRandomSuperheroIndex()]++;
        }

        Arrays.sort(votingDistribution, (a, b) -> b - a);

        List<String> superheroesPool = new ArrayList<>(Arrays.asList(SUPERHERO_NAMES));
        int currentRank = 0;
        int rankVotes = votingDistribution[0];
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(currentRank, rankVotes));
        rankings.get(currentRank).heroes.add(riggedHero);
        superheroesPool.remove(riggedHero);

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

        System.out.println(String.join("\n", rankings.stream().map(ranking -> ranking.toString()).toArray(String[]::new)));
    }

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;

public class HeroVotingManager {
    private static class Ranking {
        public final int RANK_NUMBER;
        public final int NUM_VOTES;
        public final List<String> heroes = new ArrayList<>();

        public Ranking(int rankNumber, int numVotes) {
            this.RANK_NUMBER = rankNumber;
            this.NUM_VOTES = numVotes;
        }

        @Override
        public String toString() {
            return String.format(
                "Rank %s (%s votes): %s",
                RANK_NUMBER,
                NUM_VOTES,
                String.join(", ", heroes)
            );
        }
    }

    public static final String[] SUPERHERO_NAMES = {"Iron Man", "Spiderman", "Captain America"};
    public static final String MENU_TEXT = String.join("\n",
        "--> Superhero Contest! <--",
        "1. Randomized Voting",
        "2. Rigged Voting",
        "3. Quit",
        "\n> "
    );

    private final ValidatedScanner scanner;
    private final ValidatedScanner.StringInputParser<Integer> menuChoiceParser = inputString -> {
        int value = Integer.parseInt(inputString);

        if(value < 1 || value > 3) throw new RuntimeException("The integer has to be between 1 and 3 (inclusive)");

        return value;
    };

    private final ValidatedScanner.StringInputParser<Integer> wholeNumberParser = inputString -> {
        int value = Integer.parseInt(inputString);

        if(value < 0) throw new RuntimeException("Input must be a positive integer or 0!");

        return value;
    };

    public HeroVotingManager(ValidatedScanner scanner) {
        this.scanner = scanner;
    }

    private int getRandomSuperheroIndex() {
        return (int) (Math.random() * SUPERHERO_NAMES.length);
    }

    public void randomizedVoting() {
        int numVoters = scanner.readInput("How many people are voting?\n> ", wholeNumberParser);

        int[] votingDistribution = new int[SUPERHERO_NAMES.length];
        Arrays.fill(votingDistribution, 0);

        for(int i = 0; i < numVoters; i++) {
            votingDistribution[getRandomSuperheroIndex()]++;
        }

        List<Ranking> rankingText = new ArrayList<>(SUPERHERO_NAMES.length);

        
    }

    public void riggedVoting() {

    }

    public boolean menuLoop() {
        int choice = scanner.readInput(MENU_TEXT, menuChoiceParser);

        switch (choice) {
            case 1:
                randomizedVoting();
                break;
            case 2:
                riggedVoting();
                break;
            case 3:
                return false;
        }

        return true;
    }
}

public class Main {
    private static final ValidatedScanner scanner = new ValidatedScanner();
    private static final HeroVotingManager votingManager = new HeroVotingManager(scanner);

    public static void main(String[] args) {
        // if(!SecurityCheck.check(scanner)) return;

        while (votingManager.menuLoop()) {} // Continuously run "game loop"

        System.out.println("Thanks for using this program, see you next time!");
    }
}
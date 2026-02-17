import java.util.Optional;

public class StringManipulator {
    public final String text1;
    public final String text2;

    private final String combined;
    private final int combinedLength;
    private final String firstHalfOFText1;
    private Optional<Integer> firstIndexValue;
    private final String text1Swapped;

    public StringManipulator(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;

        this.combined = text1 + text2;
        this.combinedLength = combined.length();
        this.firstHalfOFText1 = text1.substring(0, Math.floorDiv(text1.length(), 2));
        
        char firstLetterOfText2 = text2.charAt(0);
        
        for(int i = 0; i < text1.length(); i++) {
            if(text1.charAt(i) == firstLetterOfText2) {
                firstIndexValue = Optional.of(i);
                break;
            }
        }

        if(firstIndexValue == null) firstIndexValue = Optional.empty();
    }

    public StringManipulator() {
        this("Hello", " World!");
    }
}

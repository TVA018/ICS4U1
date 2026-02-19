import java.util.Optional;

public class StringManipulator {
    public final String text1;
    public final String text2;

    private final String combined;
    private final int combinedLength;
    private final String firstHalfOFText1;
    private final Optional<Integer> firstIndexValue;
    private final String text1Swapped;

    public StringManipulator(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;

        // Pre-calculate values because they are immutable
        this.combined = text1 + text2;
        this.combinedLength = combined.length();
        this.firstHalfOFText1 = text1.substring(0, Math.floorDiv(text1.length(), 2));
        
        char firstLetterOfText2 = text2.charAt(0);
        
        // Have to use an "intermediate"/temporary variable otherwise the compiler complains
        Optional<Integer> firstIndexIntermediateValue = Optional.empty();

        // Loop through the characters of text1 until we find the first occurence of it in text1
        for(int i = 0; i < text1.length(); i++) {
            if(text1.charAt(i) == firstLetterOfText2) {
                firstIndexIntermediateValue = Optional.of(i);
                break;
            }
        }

        firstIndexValue = firstIndexIntermediateValue; // Assign a value to the class variable

        // Swapping halves
        if(text1.length() <= 1) { // Resolve edge case of 0 or 1 letter
            text1Swapped = "";
        } else {
            int midPoint = Math.floorDiv(text1.length(), 2);
            String firstHalf = text1.substring(0, midPoint);
            String secondHalf = text1.substring(midPoint);
            text1Swapped = secondHalf + firstHalf;
        }
    }

    public StringManipulator() {
        this("Hello", " World!");
    }

    /** @return The two strings concatenated */
    public String combineStrings() {
        return combined;
    }

    /** @return The length of the strings concatenated */
    public int getCombinedLength() {
        return combinedLength;
    }

    /** @return The first half of text1 */
    public String firstHalf() {
        return firstHalfOFText1;
    }

    /** @return The index of the first letter of text2 in text1. Returns -1 if it doesn't exist */
    public int indexOfFirstLetter() {
        return firstIndexValue.isEmpty() ? -1 : firstIndexValue.get();
    }

    /** @return The first and second half of text1 swapped. If the length of text1 is odd, then the middle letter will be considered part of the second half. */
    public String swapFirstAndSecondHalf() {
        return text1Swapped;
    }
}

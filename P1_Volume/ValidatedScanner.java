import java.io.InputStream;
import java.util.Scanner;

public class ValidatedScanner {
    public interface StringInputConverter<T> {
        public abstract T convertStringInput(String inputString);
    }

    public interface Validator<T> {
        public abstract boolean validate(T input);
    }

    private final Scanner innerScanner;

    public ValidatedScanner(InputStream source) {
        innerScanner = new Scanner(source);
    }

    public ValidatedScanner() {
        this(System.in);
    }

    public void close() {
        innerScanner.close();
    }

    public <T> T readInput(String promptMessage, StringInputConverter<T> converter, Validator<T> validator) {
        while (true) {
            System.out.print(promptMessage);
            String inputString = innerScanner.nextLine();

            try {
                return converter.convertStringInput(inputString);
            } catch(Exception e) {
                System.out.printf("%s is not a valid input - %s\n", inputString, e);
            }
        }
    }

    public <T> T readInput(StringInputConverter<T> converter, Validator<T> validator) {
        return readInput("", converter, validator);
    }

    public <T> T readInput(String promptMessage, StringInputConverter<T> converter) {
        return readInput(promptMessage, converter, input -> true);
    }

    public <T> T readInput(StringInputConverter<T> converter) {
        return readInput("", converter, input -> true);
    }

    public String readLine() {
        return innerScanner.nextLine();
    }
}

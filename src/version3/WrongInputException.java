package version3;

/**
 * throws an exception if incorrect input is detected
 */
public class WrongInputException extends Throwable {
    private String input;

    WrongInputException(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Could not understand input '" + input + "'";
    }
}
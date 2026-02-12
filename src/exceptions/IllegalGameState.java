package exceptions;

public class IllegalGameState extends RuntimeException {
    public IllegalGameState(String message) {
        super(message);
    }
}

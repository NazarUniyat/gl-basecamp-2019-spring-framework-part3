package owu.exceptions;

public class DatabaseException extends RuntimeException {
    public static final String EXCEPTION = "DB querying exception occurred:  ";

    public DatabaseException(String message) {
        super(EXCEPTION + message);
    }
}

package app.exceptions;

public class AppDatabaseException extends Exception {


    public final static  String NO_RESULT_FOUND = "exception.database.no.result.found";

    public AppDatabaseException(String message) {
        super(message);
    }

    public AppDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

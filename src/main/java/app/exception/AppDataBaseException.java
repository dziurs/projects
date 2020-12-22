package app.exception;

public class AppDataBaseException extends BuildingSalesAppException {

    static final private String KEY_OPTIMISTIC_LOCK = "exception.optimistic.lock";

    public AppDataBaseException() {
        super(KEY_OPTIMISTIC_LOCK);
    }

    public AppDataBaseException(Throwable cause) {
        super(KEY_OPTIMISTIC_LOCK, cause);
    }
}

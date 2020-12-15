package app.exception;

public class GeneralApplicationException extends BuildingSalesAppException {

    static final public String INTERCEPTOR = "exception.interceptor.logger";
    static final public String CRYPTER = "exception.crypter.argument";
    static final public String PRINCIPAL= "exception.principal.is.null";
    static final public String REVIEW= "exception.review.is.null";
    static final public String KEY_OPTIMISTIC_LOCK = "exception.optimistic.lock.general";

    public GeneralApplicationException(String message) {
        super(message);
    }

    public GeneralApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

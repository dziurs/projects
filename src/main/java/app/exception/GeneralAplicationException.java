package app.exception;

public class GeneralAplicationException extends BuildingSalesAppException {

    static final public String INTERCEPTOR = "exception.interceptor.logger";
    static final public String CRYPTER = "exception.crypter.argument";
    static final public String PRINCIPAL= "exception.principal.is.null";

    public GeneralAplicationException(String message) {
        super(message);
    }

    public GeneralAplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

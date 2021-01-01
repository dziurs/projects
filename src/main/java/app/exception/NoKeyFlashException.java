package app.exception;

public class NoKeyFlashException extends BuildingSalesAppException {

    public final static String EMPTY_FLASH = "exception.flash.scope.empty.value.or.null";

    public NoKeyFlashException(String message) {
        super(message);
    }
}

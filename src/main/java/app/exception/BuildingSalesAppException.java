package app.exception;
import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
abstract public class BuildingSalesAppException extends Exception{

    protected BuildingSalesAppException(String message) {
        super(message);
    }
    protected BuildingSalesAppException(String message, Throwable cause){
        super(message,cause);
    }
}

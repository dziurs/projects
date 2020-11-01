package app.exception;

public class AccountException extends BuildingSalesAppException{

    static final public String EMAIL_WAS_USED = "exception.account.email.check";
    static final public String KEY_OPTIMISTIC_LOCK = "exception.optimistic.lock.account";
    static final public String LOGIN = "exception.account.login";

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}

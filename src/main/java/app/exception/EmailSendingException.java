package app.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class EmailSendingException extends Exception {

    static final public String EMAIL_ERROR = "register.user.controller.email.sending.error";
    static final public String EMAIL_OR_PID_ERROR = "activate.user.error";

    public EmailSendingException(String message) {
        super(message);
    }
}

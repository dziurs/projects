package app.exception;

public class ImegeFileIOException extends BuildingSalesAppException {

    static final public String IMAGE_FILE= "exception.during.write.file.with.image";

    public ImegeFileIOException(String message) {
        super(message);
    }

    public ImegeFileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

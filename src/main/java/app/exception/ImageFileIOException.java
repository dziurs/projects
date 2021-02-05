package app.exception;

public class ImageFileIOException extends BuildingSalesAppException {

    static final public String IMAGE_FILE= "exception.during.write.file.with.image";

    public ImageFileIOException(String message) {
        super(message);
    }

    public ImageFileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

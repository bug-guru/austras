package guru.bug.austras.web.errors;

public class UnsupportedMediaTypeException extends AustrasHttpException {
    public UnsupportedMediaTypeException() {
        super(415, "Unsupported Media Type");
    }
}

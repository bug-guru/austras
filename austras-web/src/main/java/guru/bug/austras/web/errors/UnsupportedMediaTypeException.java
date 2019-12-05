package guru.bug.austras.web.errors;

public class UnsupportedMediaTypeException extends HttpException {
    public UnsupportedMediaTypeException() {
        super(415, "Unsupported Media Type");
    }
}

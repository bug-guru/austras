package guru.bug.austras.convert.json.utils;

public class JsonReadingException extends JsonException {

    protected JsonReadingException() {
    }

    public JsonReadingException(String message) {
        super(message);
    }

    protected JsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonReadingException(Throwable cause) {
        super(cause);
    }
}

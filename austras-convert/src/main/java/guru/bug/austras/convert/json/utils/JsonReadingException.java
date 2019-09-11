package guru.bug.austras.convert.json.utils;

public class JsonReadingException extends JsonException {

    public JsonReadingException() {
    }

    public JsonReadingException(String message) {
        super(message);
    }

    public JsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonReadingException(Throwable cause) {
        super(cause);
    }
}

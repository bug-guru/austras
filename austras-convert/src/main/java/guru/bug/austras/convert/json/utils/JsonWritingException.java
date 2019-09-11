package guru.bug.austras.convert.json.utils;

public class JsonWritingException extends JsonException {
    public JsonWritingException() {
    }

    public JsonWritingException(String message) {
        super(message);
    }

    public JsonWritingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonWritingException(Throwable cause) {
        super(cause);
    }
}

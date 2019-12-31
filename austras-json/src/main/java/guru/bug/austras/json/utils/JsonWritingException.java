package guru.bug.austras.json.utils;

public class JsonWritingException extends JsonException {

    public JsonWritingException(String message) {
        super(message);
    }

    public JsonWritingException(Throwable cause) {
        super(cause);
    }
}

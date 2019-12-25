package guru.bug.austras.json.utils;

class JsonException extends RuntimeException {

    JsonException(String message) {
        super(message);
    }

    JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    JsonException(Throwable cause) {
        super(cause);
    }
}

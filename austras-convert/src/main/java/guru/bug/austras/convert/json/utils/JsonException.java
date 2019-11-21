package guru.bug.austras.convert.json.utils;

class JsonException extends RuntimeException {

    JsonException() {
    }

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

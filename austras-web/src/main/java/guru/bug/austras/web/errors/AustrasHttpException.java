package guru.bug.austras.web.errors;

public class AustrasHttpException extends RuntimeException {
    private int statusCode;

    public AustrasHttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

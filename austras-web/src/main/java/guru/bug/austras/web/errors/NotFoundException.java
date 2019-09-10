package guru.bug.austras.web.errors;

public class NotFoundException extends AustrasHttpException {
    public NotFoundException() {
        super(404, "Not Found");
    }
}

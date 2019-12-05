package guru.bug.austras.web.errors;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(404, "Not Found");
    }
}

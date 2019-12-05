package guru.bug.austras.web.errors;

public class MultipleChoicesException extends HttpException {
    // TODO For request methods other than HEAD, the server SHOULD generate a
    //   payload in the 300 response containing a list of representation
    //   metadata and URI reference(s) from which the user or user agent can
    //   choose the one most preferred.
    public MultipleChoicesException() {
        super(300, "Multiple Choices");
    }
}

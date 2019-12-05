package guru.bug.austras.web.errors;

public class MethodNotAllowedException extends HttpException {
    // TODO The server MUST generate an Allow header field in a 405 response containing a list of the target resource's currently supported methods.
    public MethodNotAllowedException() {
        super(405, "Method Not Allowed");
    }
}

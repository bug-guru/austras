package guru.bug.austras.web.errors;

public class NotAcceptableException extends AustrasHttpException {
    // TODO If a server returns such an error status, the body of the message should contain the list of the available
    //  representations of the resources, allowing the user to choose among them.
    public NotAcceptableException() {
        super(406, "Not Acceptable");
    }
}

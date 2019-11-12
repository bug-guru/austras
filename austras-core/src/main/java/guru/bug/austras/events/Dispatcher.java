package guru.bug.austras.events;

public interface Dispatcher<M> {
    String MESSAGE_QUALIFIER_NAME = "austras.Message";

    void dispatch(M message);
}

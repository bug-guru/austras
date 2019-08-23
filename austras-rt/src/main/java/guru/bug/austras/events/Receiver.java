package guru.bug.austras.events;

public interface Receiver<M> {
    String MESSAGE_QUALIFIER_NAME = "austras.Message";

    void receive(M message);
}

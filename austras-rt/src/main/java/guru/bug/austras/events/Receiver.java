package guru.bug.austras.events;

public interface Receiver<M> {
    void receive(M message);
}

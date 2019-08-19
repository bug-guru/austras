package guru.bug.austras.events;

public interface Broadcaster<M> {
    void send(M message);
}

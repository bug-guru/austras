package guru.bug.austras.events;

public interface Broadcast<M> {
    void send(M message);
}

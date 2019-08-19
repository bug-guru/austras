package guru.bug.austras.events;

public interface Receiver<M> {
    void process(M message);
}

package guru.bug.austras.events;

public interface Dispatcher<M> {

    void dispatch(M message);

}

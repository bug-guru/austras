package guru.bug.austras.events;

import java.util.Collection;
import java.util.List;

public abstract class Broadcaster<M> {
    private final List<Dispatcher<M>> dispatchers;

    public Broadcaster(Collection<? extends Dispatcher<M>> dispatchers) {
        this.dispatchers = List.copyOf(dispatchers);
    }

    public void send(M message) {
        dispatchers.forEach(r -> r.dispatch(message));
    }
}

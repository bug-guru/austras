package guru.bug.austras.events;

import java.util.Collection;
import java.util.List;

public abstract class Broadcaster<M> {
    private final List<Receiver<M>> receivers;

    public Broadcaster(Collection<? extends Receiver<M>> receivers) {
        this.receivers = List.copyOf(receivers);
    }

    public void send(M message) {
        receivers.forEach(r -> {
            try {

                r.receive(message);

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);// TODO
            }
        });
    }
}

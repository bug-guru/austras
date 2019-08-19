package guru.bug.austras.events;

import java.util.List;

public class BroadcasterImpl<M> implements Broadcaster<M> {
    private final List<Receiver<M>> receivers;

    public BroadcasterImpl(Receiver<M>... receivers) {
        this.receivers = List.of(receivers);
    }

    @Override
    public void send(M message) {
        receivers.forEach(r -> {
            try {

                r.process(message);

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);// TODO
            }
        });
    }
}

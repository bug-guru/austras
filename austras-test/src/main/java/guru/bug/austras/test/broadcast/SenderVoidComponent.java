package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.events.Message;

public class SenderVoidComponent {
    private final Broadcaster<Void> broadcaster;

    public SenderVoidComponent(@Qualifier(name = "void") @Message Broadcaster<Void> broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void sendNotification() {
        broadcaster.send(null);
    }
}

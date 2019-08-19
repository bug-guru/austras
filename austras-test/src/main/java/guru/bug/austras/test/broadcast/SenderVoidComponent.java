package guru.bug.austras.test.broadcast;

import guru.bug.austras.events.Broadcaster;

public class SenderVoidComponent {
    private final Broadcaster<Void> broadcaster;

    public SenderVoidComponent(Broadcaster<Void> broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void sendNotification() {
        broadcaster.send(null);
    }
}

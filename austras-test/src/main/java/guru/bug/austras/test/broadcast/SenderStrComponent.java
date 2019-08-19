package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Broadcaster;

public class SenderStrComponent {

    private final Broadcaster<String> broadcaster;

    public SenderStrComponent(@Qualifier(name = "hello") Broadcaster<String> broadcaster) {

        this.broadcaster = broadcaster;
    }

    public void sendHello() {
        broadcaster.send("Hello, World!");
    }
}

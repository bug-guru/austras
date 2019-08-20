package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Component;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.events.Message;

@Component
public class SenderStrComponent {

    private final Broadcaster<String> broadcaster;

    public SenderStrComponent(@Qualifier(name = "hello") @Message Broadcaster<String> broadcaster) {

        this.broadcaster = broadcaster;
    }

    public void sendHello() {
        broadcaster.send("Hello, World!");
    }
}

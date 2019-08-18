package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Broadcast;

public class SenderComponent {

    private final Broadcast<String> broadcast;

    public SenderComponent(@Qualifier(name = "hello") Broadcast<String> broadcast) {

        this.broadcast = broadcast;
    }

    public void sendHello() {
        broadcast.send("Hello, World!");
    }
}

package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Component;
import guru.bug.austras.core.qualifiers.Broadcast;

@Component
public class SenderStrComponent {

    private final StrEvents strEvents;

    public SenderStrComponent(@Broadcast StrEvents strEvents) {
        this.strEvents = strEvents;
    }

    public void sendHello() {
        strEvents.processString("Hello, World!");
    }
}

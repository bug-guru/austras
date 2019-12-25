package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.qualifiers.Broadcast;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class SenderStrComponent {

    private final StrEvents strEvents;

    public SenderStrComponent(@Broadcast StrEvents strEvents) {
        this.strEvents = strEvents;
    }

    public void sendHello() {
        strEvents.processString("Hello, World!");
    }
}

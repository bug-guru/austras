package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Receive;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ServiceExecutor;

public class ReceiverVoid1Component {

    @Qualifier(name = "void")
    @Receive
    public void receive(ComponentA componentA, ServiceExecutor serviceExecutor) {
        System.out.println("ReceiverVoid1: received notification");
    }

}

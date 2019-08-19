package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Receive;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ComponentE;
import guru.bug.austras.test.ServiceExecutor;

public class ReceiverVoid2Component {

    @Qualifier(name = "void")
    @Receive
    public void receive(ComponentE componentE) {
        System.out.println("ReceiverVoid2: received notification");
    }

}

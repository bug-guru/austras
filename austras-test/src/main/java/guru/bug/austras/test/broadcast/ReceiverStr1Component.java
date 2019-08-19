package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Receive;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ServiceExecutor;

public class ReceiverStr1Component {

    public void receive(@Qualifier(name = "hello") @Receive String msg, ComponentA componentA, ServiceExecutor serviceExecutor) {
        System.out.println("ReceiverStr1: received [" + msg + "]");
    }

}

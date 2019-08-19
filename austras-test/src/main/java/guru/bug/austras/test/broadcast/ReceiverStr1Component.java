package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ServiceExecutor;

public class ReceiverStr1Component {

    public void receive(@Qualifier(name = "hello") @Message String msg, ComponentA componentA, ServiceExecutor serviceExecutor) {
        System.out.println("ReceiverStr1: received [" + msg + "]");
    }

}

package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import guru.bug.austras.test.ComponentC;
import guru.bug.austras.test.chain.CompChain4;

public class Receiver2Component {

    public void receive(@Qualifier(name = "hello") @Message String msg, ComponentC componentC, CompChain4 cc4) {
        System.out.println("Receiver2: received [" + msg + "]");
    }

}

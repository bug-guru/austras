package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiverStr1Component {
    private static final Logger log = LoggerFactory.getLogger(ReceiverStr1Component.class);

    public void receive(@Qualifier(name = "hello") @Message String msg, ComponentA componentA, ServiceExecutor serviceExecutor) {
        log.info("ReceiverStr1: received [{}]", msg);
    }

}

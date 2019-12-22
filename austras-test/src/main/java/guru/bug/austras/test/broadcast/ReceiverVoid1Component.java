package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import guru.bug.austras.test.ComponentA;
import guru.bug.austras.test.ServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class ReceiverVoid1Component {
    private static final Logger log = LoggerFactory.getLogger(ReceiverVoid1Component.class);

    @Message
    @Qualifier(name = "void-qualifier")
    public void receive(ComponentA componentA, ServiceExecutor serviceExecutor) {
        log.info("ReceiverVoid1: received notification");
    }

}

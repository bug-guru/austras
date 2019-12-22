package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class ReceiverVoid2Component {
    private static final Logger log = LoggerFactory.getLogger(ReceiverVoid2Component.class);

    @Message
    @Qualifier(name = "void-qualifier")
    public void receive() {
        log.info("ReceiverVoid2: received notification");
    }

}

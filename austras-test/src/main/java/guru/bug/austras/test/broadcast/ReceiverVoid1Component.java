package guru.bug.austras.test.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class ReceiverVoid1Component implements VoidEvents {
    private static final Logger log = LoggerFactory.getLogger(ReceiverVoid1Component.class);

    @Override
    public void notification() {
        log.info("ReceiverVoid1: received notification");
    }
}

package guru.bug.austras.test.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class ReceiverVoid2Component implements VoidEvents {
    private static final Logger log = LoggerFactory.getLogger(ReceiverVoid2Component.class);

    @Override
    public void notification() {
        log.info("ReceiverVoid2: received notification");
    }
}

package guru.bug.austras.test.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL") // this class is for testing only
public class ReceiverStr2Component implements StrEvents {
    private static final Logger log = LoggerFactory.getLogger(ReceiverStr2Component.class);

    @Override
    public void processString(String param) {
        log.info("ReceiverStr2: received [{}]", param);
    }
}
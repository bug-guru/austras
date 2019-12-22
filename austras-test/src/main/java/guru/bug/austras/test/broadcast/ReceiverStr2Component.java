package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.events.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SuppressWarnings("ALL") // this class is for testing only
public class ReceiverStr2Component {
    private static final Logger log = LoggerFactory.getLogger(ReceiverStr2Component.class);

    @Message
    @Qualifier(name = "hello")
    public void receive(String msg) throws IOException { //NOSONAR this is for testing purposes only
        log.info("ReceiverStr2: received [{}]", msg);
    }

}

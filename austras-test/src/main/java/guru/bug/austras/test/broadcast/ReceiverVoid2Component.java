package guru.bug.austras.test.broadcast;

import guru.bug.austras.test.ComponentE;

public class ReceiverVoid2Component {

    //    @Qualifier(name = "void")
//    @Message
    public void receive(ComponentE componentE) {
        System.out.println("ReceiverVoid2: received notification");
    }

}

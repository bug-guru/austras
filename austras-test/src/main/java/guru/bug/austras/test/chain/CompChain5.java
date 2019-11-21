package guru.bug.austras.test.chain;

import guru.bug.austras.core.Component;

@Component
@SuppressWarnings("ALL") // this class is for testing only
public class CompChain5 {
    private final CompChain4 chain4; //NOSONAR for testing purposes only

    public CompChain5(CompChain4 chain4) {
        this.chain4 = chain4;
    }
}

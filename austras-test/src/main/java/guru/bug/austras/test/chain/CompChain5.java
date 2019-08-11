package guru.bug.austras.test.chain;

import guru.bug.austras.annotations.Component;

@Component
public class CompChain5 {
    private final CompChain4 chain4;

    public CompChain5(CompChain4 chain4) {
        this.chain4 = chain4;
    }
}

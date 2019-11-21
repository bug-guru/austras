package guru.bug.austras.test.chain;

@SuppressWarnings("ALL") // this class is for testing only
public class CompChain1 {
    private final CompChain0 chain0; //NOSONAR for testing purposes only

    public CompChain1(CompChain0 chain0) {
        this.chain0 = chain0;
    }
}

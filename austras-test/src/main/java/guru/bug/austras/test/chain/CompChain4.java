package guru.bug.austras.test.chain;

@SuppressWarnings("ALL") // this class is for testing only
public class CompChain4 {
    private final CompChain3 chain3; //NOSONAR for testing purposes only

    public CompChain4(CompChain3 chain3) {
        this.chain3 = chain3;
    }
}

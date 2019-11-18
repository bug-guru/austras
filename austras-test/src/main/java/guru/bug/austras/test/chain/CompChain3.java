package guru.bug.austras.test.chain;

public class CompChain3 {
    private final CompChain2 chain2; //NOSONAR for testing purposes only

    public CompChain3(CompChain2 chain2) {
        this.chain2 = chain2;
    }
}

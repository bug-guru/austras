package guru.bug.austras.test.chain;

public class CompChain2 {
    private final CompChain1 chain1; //NOSONAR for testing purposes only

    public CompChain2(CompChain1 chain1) {
        this.chain1 = chain1;
    }
}

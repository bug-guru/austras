package guru.bug.fuzzbagel.test;

public class TestComponentDImpl extends TestComponentDAbstract {
    private final TestComponentC c;

    public TestComponentDImpl(TestComponentA a, TestComponentC c) {
        super(a);
        this.c = c;
    }
}

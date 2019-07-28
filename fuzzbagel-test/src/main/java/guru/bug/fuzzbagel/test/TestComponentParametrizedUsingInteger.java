package guru.bug.fuzzbagel.test;

public class TestComponentParametrizedUsingInteger {
    private final TestComponentParametrized<Integer> t;

    public TestComponentParametrizedUsingInteger(TestComponentParametrized<Integer> t) {

        this.t = t;
    }
}

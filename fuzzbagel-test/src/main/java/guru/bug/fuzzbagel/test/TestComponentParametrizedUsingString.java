package guru.bug.fuzzbagel.test;

public class TestComponentParametrizedUsingString {
    private final TestComponentParametrized<String> t;

    public TestComponentParametrizedUsingString(TestComponentParametrized<String> t) {

        this.t = t;
    }
}

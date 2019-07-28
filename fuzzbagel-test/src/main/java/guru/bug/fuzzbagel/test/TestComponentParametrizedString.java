package guru.bug.fuzzbagel.test;

public class TestComponentParametrizedString implements TestComponentParametrized<String> {
    @Override
    public String test(String p) {
        return p;
    }
}

package guru.bug.fuzzbagel.test;

public class TestComponentParametrizedInteger implements TestComponentParametrized<Integer> {
    @Override
    public Integer test(Integer p) {
        return p;
    }
}

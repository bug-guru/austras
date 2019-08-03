package guru.bug.fuzzbagel.test;

public class ComponentParametrizedInteger implements ComponentParametrized<Integer> {
    @Override
    public Integer test(Integer p) {
        return p;
    }
}

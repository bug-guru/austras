package guru.bug.austras.test;

public class ComponentParametrizedInteger implements ComponentParametrized<Integer> {
    @Override
    public Integer test(Integer p) {
        return p;
    }
}

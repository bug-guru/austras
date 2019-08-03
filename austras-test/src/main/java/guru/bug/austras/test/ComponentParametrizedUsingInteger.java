package guru.bug.austras.test;

public class ComponentParametrizedUsingInteger {
    private final ComponentParametrized<Integer> t;

    public ComponentParametrizedUsingInteger(ComponentParametrized<Integer> t) {

        this.t = t;
    }
}

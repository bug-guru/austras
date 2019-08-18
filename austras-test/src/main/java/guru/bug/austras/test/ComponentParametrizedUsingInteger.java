package guru.bug.austras.test;

import guru.bug.austras.core.Component;

@Component
public class ComponentParametrizedUsingInteger {
    private final ComponentParametrized<Integer> t;

    public ComponentParametrizedUsingInteger(ComponentParametrized<Integer> t) {

        this.t = t;
    }
}

package guru.bug.austras.test;

import guru.bug.austras.core.Component;

@Component
public class ComponentParametrizedUsingInteger {
    private final ComponentParametrized<Integer> t; //NOSONAR for testing purposes only

    public ComponentParametrizedUsingInteger(ComponentParametrized<Integer> t) {

        this.t = t;
    }
}

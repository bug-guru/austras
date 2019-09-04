package guru.bug.austras.test;

import guru.bug.austras.core.Component;

@Component
public class ComponentParametrizedInteger implements ComponentParametrized<Integer> {
    @Override
    public Integer test(Integer p) {
        return p;
    }
}

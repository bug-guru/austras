package guru.bug.austras.test;

import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings("ALL")
@Default
public class ComponentParametrizedInteger implements ComponentParametrized<Integer> {
    @Override
    public Integer test(Integer p) {
        return p;
    }
}

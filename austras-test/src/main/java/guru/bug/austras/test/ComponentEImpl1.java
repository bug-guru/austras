package guru.bug.austras.test;

import guru.bug.austras.core.Qualifier;

@SuppressWarnings("ALL")
@Qualifier(name = "alternative")
public class ComponentEImpl1 implements ComponentE {
    @Override
    public void doit() {
        //NOSONAR only for testing
    }
}

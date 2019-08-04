package guru.bug.austras.test;

import guru.bug.austras.provider.Provider;

public class ComponentCImplProvider implements Provider<ComponentCImpl> {

    @Override
    public ComponentCImpl get() {
        var a = new ComponentA();
        var b = new ComponentB(a);
        return new ComponentCImpl(a, b);
    }
}

package guru.bug.austras.test;

import guru.bug.austras.provider.ComponentProvider;
import guru.bug.austras.provider.GlobalComponentProvider;

public class ComponentCImplProvider extends GlobalComponentProvider<ComponentCImpl> implements ComponentProvider<ComponentCImpl> {

    @Override
    protected ComponentCImpl takeInstance() {
        var a = new ComponentA();
        var b = new ComponentB(a);
        return new ComponentCImpl(a, b);
    }
}

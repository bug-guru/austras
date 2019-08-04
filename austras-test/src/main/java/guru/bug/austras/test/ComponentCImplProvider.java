package guru.bug.austras.test;

import guru.bug.austras.provider.Provider;
import guru.bug.austras.provider.GlobalComponentProvider;

public class ComponentCImplProvider extends GlobalComponentProvider<ComponentCImpl> implements Provider<ComponentCImpl> {

    @Override
    protected ComponentCImpl takeInstance() {
        var a = new ComponentA();
        var b = new ComponentB(a);
        return new ComponentCImpl(a, b);
    }
}

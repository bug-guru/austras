package guru.bug.austras.test;

import guru.bug.austras.provider.Provider;

public class ComponentCImplProvider implements Provider<ComponentCImpl> {
    private final Provider<ComponentA> aProvider;
    private final Provider<ComponentB> bProvider;

    public ComponentCImplProvider(Provider<ComponentA> aProvider, Provider<ComponentB> bProvider) {
        this.aProvider = aProvider;
        this.bProvider = bProvider;
    }

    @Override
    public ComponentCImpl get() {
        var a = new ComponentA();
        var b = new ComponentB(a);
        return new ComponentCImpl(a, b);
    }
}

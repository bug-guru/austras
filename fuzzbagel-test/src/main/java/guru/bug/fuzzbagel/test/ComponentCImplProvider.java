package guru.bug.fuzzbagel.test;

import guru.bug.fuzzbagel.provider.ComponentProvider;
import guru.bug.fuzzbagel.provider.GlobalComponentProvider;

public class ComponentCImplProvider extends GlobalComponentProvider<ComponentCImpl> implements ComponentProvider<ComponentCImpl> {

    @Override
    protected ComponentCImpl takeInstance() {
        var a = new ComponentA();
        var b = new ComponentB(a);
        return new ComponentCImpl(a, b);
    }
}

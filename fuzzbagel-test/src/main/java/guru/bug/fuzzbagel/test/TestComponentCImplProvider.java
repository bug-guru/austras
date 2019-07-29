package guru.bug.fuzzbagel.test;

import guru.bug.fuzzbagel.provider.ComponentProvider;
import guru.bug.fuzzbagel.provider.GlobalComponentProvider;

public class TestComponentCImplProvider extends GlobalComponentProvider<TestComponentCImpl> implements ComponentProvider<TestComponentCImpl> {

    @Override
    protected TestComponentCImpl takeInstance() {
        var a = new TestComponentA();
        var b = new TestComponentB(a);
        return new TestComponentCImpl(a, b);
    }
}

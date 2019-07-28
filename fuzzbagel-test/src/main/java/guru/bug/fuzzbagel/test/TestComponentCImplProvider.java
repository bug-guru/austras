package guru.bug.fuzzbagel.test;

import guru.bug.fuzzbagel.privider.ComponentProvider;
import guru.bug.fuzzbagel.privider.GlobalComponentProvider;

public class TestComponentCImplProvider extends GlobalComponentProvider<TestComponentCImpl> implements ComponentProvider<TestComponentCImpl> {

    @Override
    protected TestComponentCImpl takeInstance() {
        return null;
    }
}

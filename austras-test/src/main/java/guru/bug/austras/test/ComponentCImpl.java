package guru.bug.austras.test;

public class ComponentCImpl implements ComponentC {

    private final ComponentA a;
    private final ComponentB b;

    public ComponentCImpl(ComponentA a, ComponentB b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void doSomething() {
    }
}

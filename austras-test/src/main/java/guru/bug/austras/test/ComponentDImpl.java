package guru.bug.austras.test;

public class ComponentDImpl extends ComponentDAbstract {
    private final ComponentC c; //NOSONAR for testing purposes only

    public ComponentDImpl(ComponentA a, ComponentC c) {
        super(a);
        this.c = c;
    }
}

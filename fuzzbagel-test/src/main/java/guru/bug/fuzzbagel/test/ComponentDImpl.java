package guru.bug.fuzzbagel.test;

public class ComponentDImpl extends ComponentDAbstract {
    private final ComponentC c;

    public ComponentDImpl(ComponentA a, ComponentC c) {
        super(a);
        this.c = c;
    }
}

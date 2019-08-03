package guru.bug.fuzzbagel.test;

public abstract class ComponentDAbstract implements ComponentD {

    private final ComponentA a;

    public ComponentDAbstract(ComponentA a) {
        this.a = a;
    }
}

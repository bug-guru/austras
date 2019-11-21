package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public abstract class ComponentDAbstract implements ComponentD {

    private final ComponentA a; //NOSONAR for testing purposes only

    public ComponentDAbstract(ComponentA a) {
        this.a = a;
    }
}

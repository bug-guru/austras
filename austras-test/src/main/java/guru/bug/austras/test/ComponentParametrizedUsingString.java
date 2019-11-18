package guru.bug.austras.test;

public class ComponentParametrizedUsingString {
    private final ComponentParametrized<String> t; //NOSONAR for testing purposes only

    public ComponentParametrizedUsingString(ComponentParametrized<String> t) {

        this.t = t;
    }
}

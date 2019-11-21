package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public class ComponentParametrizedUsingString {
    private final ComponentParametrized<String> t; //NOSONAR for testing purposes only

    public ComponentParametrizedUsingString(ComponentParametrized<String> t) {

        this.t = t;
    }
}

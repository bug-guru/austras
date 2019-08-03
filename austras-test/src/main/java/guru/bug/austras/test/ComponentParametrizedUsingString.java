package guru.bug.austras.test;

public class ComponentParametrizedUsingString {
    private final ComponentParametrized<String> t;

    public ComponentParametrizedUsingString(ComponentParametrized<String> t) {

        this.t = t;
    }
}

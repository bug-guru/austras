package guru.bug.austras.test;

public class ComponentParametrizedString implements ComponentParametrized<String> {
    @Override
    public String test(String p) {
        return p;
    }
}

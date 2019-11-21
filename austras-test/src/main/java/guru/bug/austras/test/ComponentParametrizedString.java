package guru.bug.austras.test;

@SuppressWarnings("ALL")
public class ComponentParametrizedString implements ComponentParametrized<String> {
    @Override
    public String test(String p) {
        return p;
    }
}

package guru.bug.fuzzbagel.test;

public class ComponentParametrizedUsingString {
    private final ComponentParametrized<String> t;

    public ComponentParametrizedUsingString(ComponentParametrized<String> t) {

        this.t = t;
    }
}

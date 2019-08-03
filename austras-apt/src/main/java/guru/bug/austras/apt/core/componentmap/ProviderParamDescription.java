package guru.bug.austras.apt.core.componentmap;

public class ProviderParamDescription {
    private final ComponentKey key;
    private final String varName;


    public ProviderParamDescription(ComponentKey key, String varName) {
        this.key = key;
        this.varName = varName;
    }

    public ComponentKey getKey() {
        return key;
    }

    public String getVarName() {
        return varName;
    }
}

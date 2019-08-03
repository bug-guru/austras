package guru.bug.austras.apt.core.componentmap;

import javax.lang.model.element.TypeElement;

public class ComponentDescription {
    private final String qualifier;
    private final String varName;
    private final TypeElement type;
    private ProviderDescription provider;

    public ComponentDescription(String varName, TypeElement componentType, String qualifier) {
        this.varName = varName;
        this.type = componentType;
        this.qualifier = qualifier;
    }

    public String getVarName() {
        return varName;
    }

    public TypeElement getType() {
        return type;
    }

    public ProviderDescription getProvider() {
        return provider;
    }

    public void setProvider(ProviderDescription provider) {
        this.provider = provider;
    }

    public String getQualifier() {
        return qualifier;
    }
}

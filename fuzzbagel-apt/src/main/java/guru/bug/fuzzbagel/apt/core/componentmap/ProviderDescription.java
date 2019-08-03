package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.List;

public class ProviderDescription {
    private final String varName;
    private final TypeElement type;
    private final List<ProviderParamDescription> params;

    public ProviderDescription(String varName, TypeElement componentType, List<ProviderParamDescription> params) {
        this.varName = varName;
        type = componentType;
        this.params = List.copyOf(params);
    }

    public String getVarName() {
        return varName;
    }

    public TypeElement getType() {
        return type;
    }

    public List<ProviderParamDescription> getParams() {
        return params;
    }
}

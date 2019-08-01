package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

public class ProviderDescription extends BaseDescription {
    private final List<ProviderParamDescription> params;

    public ProviderDescription(String varName, TypeElement componentType, List<ProviderParamDescription> params) {
        super(varName, componentType);
        this.params = List.copyOf(params);
    }

    public List<ProviderParamDescription> getParams() {
        return params;
    }
}

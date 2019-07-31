package guru.bug.fuzzbagel.apt.core.componentmap;

import guru.bug.fuzzbagel.apt.core.annotations.Provider;

import javax.lang.model.element.TypeElement;
import java.util.Objects;

public class ComponentDescription extends BaseDescription {
    private ProviderDescription provider;

    public ComponentDescription(String varName, TypeElement componentType) {
        super(varName, componentType);
    }

    public ProviderDescription getProvider() {
        return provider;
    }

    public void setProvider(ProviderDescription provider) {
        this.provider = provider;
    }

}

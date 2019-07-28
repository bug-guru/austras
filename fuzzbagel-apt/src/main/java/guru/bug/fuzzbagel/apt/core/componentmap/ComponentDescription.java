package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;

public class ComponentDescription {
    private final TypeElement componentType;
    private TypeElement providerType;

    public ComponentDescription(TypeElement componentType) {
        this.componentType = componentType;
    }

    public TypeElement getComponentType() {
        return componentType;
    }

    public TypeElement getProviderType() {
        return providerType;
    }

    public void setProviderType(TypeElement providerType) {
        this.providerType = providerType;
    }

    @Override
    public String toString() {
        return "ComponentDescription{" +
                "componentType=" + componentType +
                ", providerType=" + providerType +
                '}';
    }
}

package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentDescription that = (ComponentDescription) o;
        return componentType.equals(that.componentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentType);
    }

    @Override
    public String toString() {
        return "ComponentDescription{" +
                "componentType=" + componentType +
                ", providerType=" + providerType +
                '}';
    }
}

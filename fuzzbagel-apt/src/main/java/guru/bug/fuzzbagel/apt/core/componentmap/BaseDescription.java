package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.Objects;

public abstract class BaseDescription {
    private final String varName;
    private final TypeElement componentType;

    public BaseDescription(String varName, TypeElement componentType) {
        this.varName = varName;
        this.componentType = componentType;
    }

    public String getVarName() {
        return varName;
    }

    public TypeElement getComponentType() {
        return componentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDescription that = (BaseDescription) o;
        return componentType.equals(that.componentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentType);
    }
}

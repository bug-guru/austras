package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.Objects;

public abstract class BaseDescription {
    private final String varName;
    private final TypeElement type;

    public BaseDescription(String varName, TypeElement type) {
        this.varName = varName;
        this.type = type;
    }

    public String getVarName() {
        return varName;
    }

    public TypeElement getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDescription that = (BaseDescription) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

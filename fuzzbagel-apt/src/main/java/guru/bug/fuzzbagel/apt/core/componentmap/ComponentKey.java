package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.type.DeclaredType;
import java.util.Objects;

public class ComponentKey implements Comparable<ComponentKey> {
    private final DeclaredType type;
    private final String typeAsString;

    public ComponentKey(DeclaredType type) {
        this.type = type;
        this.typeAsString = type.toString();
    }

    public DeclaredType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentKey that = (ComponentKey) o;
        return typeAsString.equals(that.typeAsString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeAsString);
    }

    @Override
    public int compareTo(ComponentKey o) {
        return this.typeAsString.compareTo(o.typeAsString);
    }
}

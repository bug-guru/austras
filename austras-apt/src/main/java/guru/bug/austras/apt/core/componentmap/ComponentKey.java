package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.model.QualifierModel;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class ComponentKey {
    private final String type;
    private final Set<QualifierModel> qualifiers;

    public ComponentKey(String type, Collection<QualifierModel> qualifiers) {
        this.type = type;
        this.qualifiers = Set.copyOf(qualifiers);
    }

    public String getType() {
        return type;
    }

    public Set<QualifierModel> getQualifier() {
        return qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentKey that = (ComponentKey) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, qualifiers);
    }

    @Override
    public String toString() {
        return "ComponentKey{" +
                "type='" + type + '\'' +
                ", qualifiers=" + qualifiers +
                '}';
    }
}

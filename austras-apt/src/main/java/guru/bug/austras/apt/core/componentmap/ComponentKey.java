package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.model.QualifierModel;

import java.util.Objects;

public class ComponentKey {
    private final String type;
    private final QualifierModel qualifiers;

    public ComponentKey(String type, QualifierModel qualifiers) {
        this.type = type;
        if (qualifiers != null && !qualifiers.isEmpty()) {
            this.qualifiers = qualifiers;
        } else {
            this.qualifiers = null;
        }
    }

    public String getType() {
        return type;
    }

    public QualifierModel getQualifier() {
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

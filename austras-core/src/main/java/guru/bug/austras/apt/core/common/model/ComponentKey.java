package guru.bug.austras.apt.core.common.model;

import java.util.Objects;

public class ComponentKey implements Comparable<ComponentKey> {
    private final String type;
    private final QualifierSetModel qualifiers;

    private ComponentKey(String type, QualifierSetModel qualifiers) {
        this.type = type;
        if (qualifiers == null || qualifiers.isEmpty()) {
            this.qualifiers = QualifierSetModel.ofDefault();
        } else {
            this.qualifiers = qualifiers;
        }
    }

    public static ComponentKey of(String type) {
        return new ComponentKey(type, QualifierSetModel.ofDefault());
    }

    public static ComponentKey of(String type, QualifierSetModel qualifiers) {
        return new ComponentKey(type, qualifiers);
    }

    public static ComponentKey of(Class<?> type, QualifierSetModel qualifiers) {
        return new ComponentKey(type.getName(), qualifiers);
    }

    public static ComponentKey ofAny(String type) {
        return new ComponentKey(type, QualifierSetModel.ofAny());
    }

    public String getType() {
        return type;
    }

    public QualifierSetModel getQualifier() {
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

    @Override
    public int compareTo(ComponentKey o) {
        var result = this.type.compareTo(o.type);
        if (result == 0) {
            result = this.qualifiers.compareTo(o.qualifiers);
        }
        return result;
    }
}

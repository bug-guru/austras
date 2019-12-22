package guru.bug.austras.apt.core.model;

import java.util.Objects;

public class DependencyModel {
    private String name;
    private String type;
    private QualifierModel qualifiers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QualifierModel getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(QualifierModel qualifiers) {
        this.qualifiers = qualifiers;
    }

    public ComponentKey asComponentKey() {
        return new ComponentKey(type, qualifiers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyModel that = (DependencyModel) o;
        return name.equals(that.name) &&
                type.equals(that.type) &&
                Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, qualifiers);
    }
}

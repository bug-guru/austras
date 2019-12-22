package guru.bug.austras.apt.core.model;

import guru.bug.austras.core.Provider;
import guru.bug.austras.core.Selector;

import java.util.Objects;

public class DependencyModel {
    private String name;
    private String type;
    private DependencyWrappingType wrappingType = DependencyWrappingType.NONE;
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

    public DependencyWrappingType getWrappingType() {
        return wrappingType;
    }

    public void setWrappingType(DependencyWrappingType wrappingType) {
        this.wrappingType = wrappingType;
    }

    public QualifierModel getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(QualifierModel qualifiers) {
        this.qualifiers = qualifiers;
    }

    public DependencyModel copyAsProvider() {
        var result = new DependencyModel();
        result.setType(type);
        result.setQualifiers(qualifiers);
        switch (wrappingType) {
            case NONE:
                result.setWrappingType(DependencyWrappingType.PROVIDER);
                result.setName(name + "Provider");
                break;
            case PROVIDER:
                result.setWrappingType(wrappingType);
                result.setName(name);
                break;
            case SELECTOR:
                throw new IllegalArgumentException("SELECTOR cannot be copied as PROVIDER");
            default:
                throw new IllegalArgumentException(wrappingType.name());
        }
        return result;
    }

    public DependencyModel copyAsSelector() {
        var result = new DependencyModel();
        result.setType(type);
        result.setQualifiers(qualifiers);
        switch (wrappingType) {
            case NONE:
                result.setWrappingType(DependencyWrappingType.SELECTOR);
                result.setName(name + "Selector");
                break;
            case PROVIDER:
                throw new IllegalArgumentException("PROVIDER cannot be copied as SELECTOR");
            case SELECTOR:
                result.setWrappingType(wrappingType);
                result.setName(name);
                break;
            default:
                throw new IllegalArgumentException(wrappingType.name());
        }
        return result;
    }

    public String asTypeDeclaration() {
        switch (wrappingType) {
            case NONE:
                return type;
            case PROVIDER:
                return Provider.class.getName() + "<? extends " + type + ">";
            case SELECTOR:
                return Selector.class.getName() + "<? extends " + type + ">";
            default:
                throw new IllegalArgumentException(wrappingType.name());
        }
    }

    public String asParameterDeclaration() {
        return asTypeDeclaration() + " " + getName();
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
                wrappingType == that.wrappingType &&
                Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, wrappingType, qualifiers);
    }
}

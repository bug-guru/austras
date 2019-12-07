package guru.bug.austras.apt.core.model;

import guru.bug.austras.provider.Provider;

import java.util.Collection;
import java.util.Objects;

public class DependencyModel {
    private String name;
    private String type;
    private boolean provider;
    private boolean collection;
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

    public boolean isProvider() {
        return provider;
    }

    public void setProvider(boolean provider) {
        this.provider = provider;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public QualifierModel getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(QualifierModel qualifiers) {
        this.qualifiers = qualifiers;
    }

    public DependencyModel copyAsProvider() {
        var result = new DependencyModel();
        result.setCollection(collection);
        result.setType(type);
        result.setQualifiers(qualifiers);
        result.setProvider(true);
        if (provider) {
            result.setName(name + "_");
        } else {
            result.setName(name + "Provider");
        }
        return result;
    }

    public String asTypeDeclaration() {
        var result = type;
        if (collection) {
            result = Collection.class.getName() + "<? extends " + result + ">";
        }
        if (provider) {
            result = Provider.class.getName() + "<? extends " + result + ">";
        }
        return result;
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
        return provider == that.provider &&
                collection == that.collection &&
                type.equals(that.type) &&
                Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, provider, collection, qualifiers);
    }
}

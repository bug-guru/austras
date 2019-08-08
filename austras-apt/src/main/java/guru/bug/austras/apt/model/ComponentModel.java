package guru.bug.austras.apt.model;

import java.util.List;

public class ComponentModel implements Comparable<ComponentModel> {
    private String name;
    private String instantiable;
    private List<QualifierModel> qualifiers;
    private List<String> types;
    private ProviderModel provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(String instantiable) {
        this.instantiable = instantiable;
    }

    public List<QualifierModel> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<QualifierModel> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public ProviderModel getProvider() {
        return provider;
    }

    public void setProvider(ProviderModel provider) {
        this.provider = provider;
    }

    @Override
    public int compareTo(ComponentModel o) {
        return instantiable.compareTo(o.instantiable);
    }

    @Override
    public String toString() {
        return "ComponentModel{" +
                "name='" + name + '\'' +
                ", instantiable='" + instantiable + '\'' +
                ", qualifiers='" + qualifiers + '\'' +
                '}';
    }
}

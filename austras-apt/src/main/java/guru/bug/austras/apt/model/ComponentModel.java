package guru.bug.austras.apt.model;

import java.util.ArrayList;
import java.util.List;

public class ComponentModel implements Comparable<ComponentModel> {
    private String name;
    private String instantiable;
    private List<QualifierModel> qualifiers;
    private CachingKind cachingKind;
    private String cacheType;
    private List<String> types;
    private ProviderModel provider;
    private List<DependencyModel> dependencies;

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

    public CachingKind getCachingKind() {
        return cachingKind;
    }

    public void setCachingKind(CachingKind cachingKind) {
        this.cachingKind = cachingKind;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public List<String> types() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
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

    public List<DependencyModel> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyModel> dependencies) {
        this.dependencies = dependencies;
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

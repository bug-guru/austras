package guru.bug.austras.apt.model;

import java.util.List;

public class ProviderModel {
    private String name;
    private String instantiable;
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

    public List<DependencyModel> getDependencies() {
        return dependencies == null ? List.of() : dependencies;
    }

    public void setDependencies(List<DependencyModel> dependencies) {
        this.dependencies = dependencies;
    }
}

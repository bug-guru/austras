package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.model.DependencyModel;

public class CallParamModel {
    private String name;
    private String type;
    private DependencyModel dependency;
    private boolean resolveProvider;

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

    public DependencyModel getDependency() {
        return dependency;
    }

    public void setDependency(DependencyModel dependency) {
        this.dependency = dependency;
    }

    public boolean isResolveProvider() {
        return resolveProvider;
    }

    public void setResolveProvider(boolean resolveProvider) {
        this.resolveProvider = resolveProvider;
    }
}

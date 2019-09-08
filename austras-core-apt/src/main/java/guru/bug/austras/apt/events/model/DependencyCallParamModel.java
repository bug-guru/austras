package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.model.DependencyModel;

public class DependencyCallParamModel extends CallParamModel {
    private DependencyModel dependency;
    private boolean resolveProvider;

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

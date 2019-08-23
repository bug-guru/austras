package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.model.DependencyModel;

public class DependencyCallParamModel extends CallParamModel {
    private DependencyModel dependency;

    public DependencyModel getDependency() {
        return dependency;
    }

    public void setDependency(DependencyModel dependency) {
        this.dependency = dependency;
    }
}

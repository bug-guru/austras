package guru.bug.austras.web.apt.model;

import guru.bug.austras.apt.core.common.model.DependencyModel;

public class DependencyRef {
    private final String varName;
    private final DependencyModel dependencyModel;

    public DependencyRef(String varName, DependencyModel dependencyModel) {
        this.varName = varName;
        this.dependencyModel = dependencyModel;
    }

    public String getVarName() {
        return varName;
    }

    public DependencyModel getDependencyModel() {
        return dependencyModel;
    }
}

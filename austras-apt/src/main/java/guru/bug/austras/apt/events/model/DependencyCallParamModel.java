package guru.bug.austras.apt.events.model;

public class DependencyCallParamModel extends CallParamModel {
    private final DependencyModel dependency;

    public DependencyCallParamModel(DependencyModel dependency) {
        this.dependency = dependency;
    }
}

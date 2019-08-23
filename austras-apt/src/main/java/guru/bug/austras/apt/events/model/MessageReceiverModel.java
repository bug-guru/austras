package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;

import java.util.ArrayList;
import java.util.List;

public class MessageReceiverModel {
    private String packageName;
    private String className;
    private List<DependencyModel> dependencies;
    private List<CallParamModel> parameters;
    private QualifierModel qualifiers;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DependencyModel> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyModel> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(DependencyModel dependency) {
        if (dependencies == null) {
            dependencies = new ArrayList<>();
        }
        dependencies.add(dependency);
    }

    public List<CallParamModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<CallParamModel> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(CallParamModel parameter) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.add(parameter);
    }

    public QualifierModel getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(QualifierModel qualifiers) {
        this.qualifiers = qualifiers;
    }
}

package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.apt.core.model.QualifierModel;

import java.util.ArrayList;
import java.util.List;

public class MessageDispatcherModel {
    private String packageName;
    private String className;
    private List<DependencyModel> dependencies;
    private List<CallParamModel> parameters;
    private QualifierModel qualifiers;
    private String messageType;
    private String methodName;
    private MessageCallParamModel messageParam;

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

    public QualifierModel getQualifier() {
        return qualifiers;
    }

    public void setQualifiers(QualifierModel qualifiers) {
        this.qualifiers = qualifiers;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMessageParam(MessageCallParamModel messageParam) {
        this.messageParam = messageParam;
    }

    public MessageCallParamModel getMessageParam() {
        return messageParam;
    }
}

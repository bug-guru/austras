package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.apt.core.model.QualifierModel;

public class MessageDispatcherModel {
    private String packageName;
    private String className;
    private DependencyModel componentDependency;
    private QualifierModel qualifiers;
    private String messageType;
    private String methodName;

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

    public DependencyModel getComponentDependency() {
        return componentDependency;
    }

    public void setComponentDependency(DependencyModel componentDependency) {
        this.componentDependency = componentDependency;
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
}

package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.core.model.QualifierModel;

public class MessageBroadcasterModel {
    private String type;
    private QualifierModel qualifier;
    private String packageName;
    private String simpleName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QualifierModel getQualifier() {
        return qualifier;
    }

    public void setQualifier(QualifierModel qualifier) {
        this.qualifier = qualifier;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}

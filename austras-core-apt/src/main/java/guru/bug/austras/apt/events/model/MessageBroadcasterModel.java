package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.core.common.model.QualifierSetModel;

public class MessageBroadcasterModel {
    private String type;
    private QualifierSetModel qualifier;
    private String packageName;
    private String simpleName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QualifierSetModel getQualifier() {
        return qualifier;
    }

    public void setQualifier(QualifierSetModel qualifier) {
        this.qualifier = qualifier;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}

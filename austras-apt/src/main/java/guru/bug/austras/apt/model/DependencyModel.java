package guru.bug.austras.apt.model;

import javax.lang.model.element.VariableElement;
import java.util.List;

public class DependencyModel {
    private String name;
    private String type;
    private boolean provider;
    private boolean collection;
    private boolean broadcaster;
    private List<QualifierModel> qualifiers;
    private VariableElement paramElement;

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

    public boolean isProvider() {
        return provider;
    }

    public void setProvider(boolean provider) {
        this.provider = provider;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(boolean broadcaster) {
        this.broadcaster = broadcaster;
    }

    public List<QualifierModel> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<QualifierModel> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public VariableElement getParamElement() {
        return paramElement;
    }

    public void setParamElement(VariableElement paramElement) {
        this.paramElement = paramElement;
    }
}

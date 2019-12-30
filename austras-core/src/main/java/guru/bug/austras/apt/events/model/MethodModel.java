package guru.bug.austras.apt.events.model;

import java.util.List;

public class MethodModel {
    private String name;
    private List<MethodParam> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MethodParam> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParam> parameters) {
        this.parameters = parameters;
    }
}

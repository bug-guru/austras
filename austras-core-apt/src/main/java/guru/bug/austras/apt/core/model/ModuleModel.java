package guru.bug.austras.apt.core.model;

import java.util.ArrayList;
import java.util.List;

public class ModuleModel {
    private List<ComponentModel> components;

    public List<ComponentModel> components() {
        if (components == null) {
            components = new ArrayList<>();
        }
        return components;
    }

    public List<ComponentModel> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentModel> components) {
        this.components = components;
    }
}

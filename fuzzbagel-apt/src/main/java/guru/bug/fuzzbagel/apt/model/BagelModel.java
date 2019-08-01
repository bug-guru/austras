package guru.bug.fuzzbagel.apt.model;

import java.util.ArrayList;
import java.util.List;

public class BagelModel {
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

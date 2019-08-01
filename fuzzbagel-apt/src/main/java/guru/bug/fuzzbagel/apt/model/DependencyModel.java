package guru.bug.fuzzbagel.apt.model;

public class DependencyModel {
    private String name;
    private ComponentKeyModel key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentKeyModel getKey() {
        return key;
    }

    public void setKey(ComponentKeyModel key) {
        this.key = key;
    }
}

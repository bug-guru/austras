package guru.bug.fuzzbagel.apt.model;

import java.util.Objects;

public class ComponentKeyModel implements Comparable<ComponentKeyModel> {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentKeyModel that = (ComponentKeyModel) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public int compareTo(ComponentKeyModel o) {
        return type.compareTo(o.type);
    }
}

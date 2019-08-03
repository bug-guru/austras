package guru.bug.austras.apt.core.componentmap;

import java.util.Comparator;
import java.util.Objects;

public class ComponentKey implements Comparable<ComponentKey> {
    private static final Comparator<String> DEFAULT_COMPARATOR = Comparator.nullsLast(Comparator.naturalOrder());
    private final String type;
    private final String qualifier;

    public ComponentKey(String type, String qualifier) {
        this.type = type;
        this.qualifier = qualifier;
    }

    public String getType() {
        return type;
    }

    public String getQualifier() {
        return qualifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentKey that = (ComponentKey) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(qualifier, that.qualifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, qualifier);
    }

    @Override
    public int compareTo(ComponentKey that) {
        var result = Objects.compare(this.type, that.type, DEFAULT_COMPARATOR);
        if (result == 0) {
            result = Objects.compare(this.qualifier, that.qualifier, DEFAULT_COMPARATOR);
        }
        return result;
    }

    @Override
    public String toString() {
        return "ComponentKey{" +
                "type='" + type + '\'' +
                ", qualifier='" + qualifier + '\'' +
                '}';
    }
}

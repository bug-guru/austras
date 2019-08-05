package guru.bug.austras.apt.core.componentmap;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class ComponentKey {
    private static final Comparator<String> DEFAULT_COMPARATOR = Comparator.nullsLast(Comparator.naturalOrder());
    private final String type;
    private final Set<String> qualifiers;

    public ComponentKey(String type, Collection<String> qualifiers) {
        this.type = type;
        this.qualifiers = Set.copyOf(qualifiers);
    }

    public String getType() {
        return type;
    }

    public Set<String> getQualifier() {
        return qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentKey that = (ComponentKey) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, qualifiers);
    }

    @Override
    public String toString() {
        return "ComponentKey{" +
                "type='" + type + '\'' +
                ", qualifiers=" + qualifiers +
                '}';
    }
}

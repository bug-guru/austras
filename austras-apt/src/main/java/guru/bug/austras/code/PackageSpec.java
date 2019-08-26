package guru.bug.austras.code;

import java.util.Objects;

public class PackageSpec implements Writable {
    private final String name;

    private PackageSpec(String name) {
        this.name = name;
    }

    public static PackageSpec of(String name) {
        return new PackageSpec(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageSpec that = (PackageSpec) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

package guru.bug.austras.code;

import java.util.Objects;

public class SimpleName implements Writable {
    private String name;

    private SimpleName(String name) {
        this.name = name;
    }

    public static SimpleName of(String simpleName) {
        // TODO validation
        return new SimpleName(simpleName);
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(CodeWriter out) {
        out.write(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleName that = (SimpleName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

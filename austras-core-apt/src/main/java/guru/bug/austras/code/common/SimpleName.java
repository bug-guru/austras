package guru.bug.austras.code.common;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

import java.util.Objects;

public class SimpleName implements Printable {
    private String name;

    private SimpleName(String name) {
        this.name = name;
    }

    public static SimpleName of(String simpleName) {
        if (simpleName == null
                || simpleName.isBlank()
                || !Character.isJavaIdentifierStart(simpleName.codePointAt(0))
                || !simpleName.codePoints().skip(1).allMatch(cp -> Character.isJavaIdentifierPart(cp) || cp == '.')) {
            throw new IllegalArgumentException("Invalid simpleName: " + simpleName);
        }
        return new SimpleName(simpleName);
    }

    public String getName() {
        return name;
    }

    @Override
    public void print(CodePrinter out) {
        out.print(name);
    }

    @Override
    public String toString() {
        return "SimpleName{" +
                "name='" + name + '\'' +
                '}';
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

    public String asString() {
        return name;
    }
}

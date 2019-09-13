package guru.bug.austras.codegen.common;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;

import java.util.Objects;

public class PackageName implements Printable {
    private static final PackageName root = new PackageName(null);
    private final String name;

    private PackageName(String name) {
        this.name = name;
    }

    public static PackageName of(String name) {
        if (name == null || name.isBlank()) {
            return root;
        }
        return new PackageName(name.trim());
    }

    public static PackageName root() {
        return PackageName.root;
    }

    public String getName() {
        return name;
    }

    public boolean isRoot() {
        return name == null || name.isEmpty();
    }

    public boolean isJavaLang() {
        return name != null && name.equals("java.lang");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageName that = (PackageName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PackageName{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void print(CodePrinter out) {
        if (!isRoot()) {
            out.print(name);
        }
    }

    public String asString() {
        return name;
    }
}

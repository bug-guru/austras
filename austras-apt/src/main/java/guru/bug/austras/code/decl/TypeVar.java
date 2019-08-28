package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

public class TypeVar implements Printable {
    private final String name;

    private TypeVar(String name) {
        this.name = name;
    }

    public static TypeVar of(String name) {
        // TODO validation
        return new TypeVar(name);
    }

    @Override
    public void print(CodePrinter out) {
        out.print(name);
    }
}

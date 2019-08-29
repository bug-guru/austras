package guru.bug.austras.code.common;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;

public class TypeVarName implements Printable {
    private final String name;

    private TypeVarName(String name) {
        this.name = name;
    }

    public static TypeVarName of(String name) {
        // TODO validation
        return new TypeVarName(name);
    }

    @Override
    public void print(CodePrinter out) {
        out.print(name);
    }
}

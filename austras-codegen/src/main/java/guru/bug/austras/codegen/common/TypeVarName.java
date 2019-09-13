package guru.bug.austras.codegen.common;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;

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

    @Override
    public String toString() {
        return "TypeVarName{" +
                "name='" + name + '\'' +
                '}';
    }
}

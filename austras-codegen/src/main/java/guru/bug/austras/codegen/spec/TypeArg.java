package guru.bug.austras.codegen.spec;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.TypeVarName;

import java.util.function.Consumer;

/**
 * Represents type parameter or type argument.
 */
public class TypeArg implements Printable {
    private static final TypeVarName WILDCARD_VAR = TypeVarName.of("?");
    private final TypeSpec bound;
    private final TypeArgBoundType boundType;
    private final TypeVarName typeVar;

    private TypeArg(TypeSpec bound, TypeArgBoundType boundType, TypeVarName typeVar) {
        this.bound = bound;
        this.boundType = boundType;
        this.typeVar = typeVar;
    }

    public static TypeArg diamond() {
        return new TypeArg(null, null, null);
    }

    public static TypeArg wildcard() {

        return new TypeArg(null, null, WILDCARD_VAR);
    }

    public static TypeArg wildcardExtends(String upperBoundQualifiedName) {
        return wildcardExtends(TypeSpec.of(upperBoundQualifiedName));
    }

    public static TypeArg wildcardExtends(TypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.EXTENDS, WILDCARD_VAR);
    }

    public static TypeArg wildcardSuper(TypeSpec lowerBound) {
        return new TypeArg(lowerBound, TypeArgBoundType.SUPER, WILDCARD_VAR);
    }

    public static TypeArg param(String varName) {
        return new TypeArg(null, null, TypeVarName.of(varName));
    }

    public static TypeArg paramExtends(String varName, TypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.EXTENDS, TypeVarName.of(varName));
    }

    public static TypeArg paramSuper(String varName, TypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.SUPER, TypeVarName.of(varName));
    }

    public static TypeArg ofType(TypeSpec type) {
        return new TypeArg(type, null, null);
    }

    public static TypeArg ofType(String typeName) {
        return ofType(TypeSpec.of(typeName));
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(typeVar)
                .print(boundType)
                .print(bound);
    }

    private enum TypeArgBoundType implements Printable {
        SUPER(CodePrinter::printSuper),
        EXTENDS(CodePrinter::printExtends);

        private final Consumer<CodePrinter> printer;

        TypeArgBoundType(Consumer<CodePrinter> printer) {
            this.printer = printer;
        }

        @Override
        public void print(CodePrinter out) {
            out.space();
            printer.accept(out);
        }
    }
}
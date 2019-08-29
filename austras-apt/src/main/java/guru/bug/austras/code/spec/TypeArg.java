package guru.bug.austras.code.spec;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.TypeVarName;

import java.util.function.Consumer;

/**
 * Represents type parameter or type argument.
 */
public class TypeArg implements Printable {
    private static final TypeVarName WILDCARD_VAR = TypeVarName.of("?");
    private final ClassTypeSpec bound;
    private final TypeArgBoundType boundType;
    private final TypeVarName typeVar;

    private TypeArg(ClassTypeSpec bound, TypeArgBoundType boundType, TypeVarName typeVar) {
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
        return wildcardExtends(ClassTypeSpec.of(upperBoundQualifiedName));
    }

    public static TypeArg wildcardExtends(ClassTypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.EXTENDS, WILDCARD_VAR);
    }

    public static TypeArg wildcardSuper(ClassTypeSpec lowerBound) {
        return new TypeArg(lowerBound, TypeArgBoundType.SUPER, WILDCARD_VAR);
    }

    public static TypeArg param(String varName) {
        return new TypeArg(null, null, TypeVarName.of(varName));
    }

    public static TypeArg paramExtends(String varName, ClassTypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.EXTENDS, TypeVarName.of(varName));
    }

    public static TypeArg paramSuper(String varName, ClassTypeSpec upperBound) {
        return new TypeArg(upperBound, TypeArgBoundType.SUPER, TypeVarName.of(varName));
    }

    public static TypeArg ofType(String typeName) {
        return new TypeArg(ClassTypeSpec.of(typeName), null, null);
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

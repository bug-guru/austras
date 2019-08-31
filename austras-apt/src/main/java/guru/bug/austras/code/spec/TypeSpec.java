package guru.bug.austras.code.spec;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.QualifiedName;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class TypeSpec implements Printable {
    private static final TypeSpec VOID_TYPE = new TypeSpec(QualifiedName.of(null, "void"), null);
    private final QualifiedName name;
    private final List<TypeArg> typeArgs;

    private TypeSpec(QualifiedName name, List<TypeArg> typeArgs) {
        this.name = name;
        this.typeArgs = typeArgs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static TypeSpec of(String qualifiedName) {
        return of(QualifiedName.of(qualifiedName));
    }

    public static TypeSpec of(QualifiedName name) {
        return new TypeSpec(name, null);
    }

    public static TypeSpec voidType() {
        return VOID_TYPE;
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(name)
                .print(out.withWeakPrefix("<").weakSuffix(">").separator(","),
                        o -> o.print(typeArgs));
    }

    public static class Builder {
        private List<TypeArg> typeArgs = new ArrayList<>();
        private QualifiedName name;

        public Builder name(QualifiedName name) {
            this.name = requireNonNull(name);
            return this;
        }

        public Builder addTypeArg(TypeArg typeArg) {
            this.typeArgs.add(requireNonNull(typeArg));
            return this;
        }

        public TypeSpec build() {
            return new TypeSpec(name, List.copyOf(typeArgs));
        }
    }
}

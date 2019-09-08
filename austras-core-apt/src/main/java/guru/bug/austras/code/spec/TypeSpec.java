package guru.bug.austras.code.spec;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.QualifiedName;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class TypeSpec implements Printable {
    private static final TypeSpec VOID_TYPE = new TypeSpec(QualifiedName.of(null, "void"), null, false);
    private final QualifiedName name;
    private final List<TypeArg> typeArgs;
    private final boolean array;

    private TypeSpec(QualifiedName name, List<TypeArg> typeArgs, boolean array) {
        this.name = name;
        this.typeArgs = typeArgs;
        this.array = array;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static TypeSpec of(Class<?> clazz, TypeArg... typeArgs) {
        return of(QualifiedName.of(clazz), typeArgs);
    }

    public static TypeSpec of(String qualifiedName) {
        // FIXME very specific case is implemented.
        if (qualifiedName.endsWith(">")) {
            var startParam = qualifiedName.indexOf('<');
            var qn = qualifiedName.substring(0, startParam);
            var tn = qualifiedName.substring(startParam + 1, qualifiedName.length() - 1);
            return of(QualifiedName.of(qn), TypeArg.ofType(tn));
        } else {
            return of(QualifiedName.of(qualifiedName));
        }
    }

    public static TypeSpec of(QualifiedName name, TypeArg... typeArgs) {
        return new TypeSpec(name, typeArgs.length == 0 ? null : List.of(typeArgs), false);
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
        if (array) {
            out.print("[]");
        }
    }

    public static class Builder {
        private List<TypeArg> typeArgs = new ArrayList<>();
        private QualifiedName name;
        private boolean array;

        public Builder name(QualifiedName name) {
            this.name = requireNonNull(name);
            return this;
        }

        public Builder addTypeArg(TypeArg typeArg) {
            this.typeArgs.add(requireNonNull(typeArg));
            return this;
        }

        public Builder array() {
            this.array = true;
            return this;
        }

        public TypeSpec build() {
            return new TypeSpec(name, List.copyOf(typeArgs), array);
        }
    }
}

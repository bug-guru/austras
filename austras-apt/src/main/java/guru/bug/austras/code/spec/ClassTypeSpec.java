package guru.bug.austras.code.spec;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.QualifiedName;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClassTypeSpec implements Printable {
    private final QualifiedName name;
    private final List<TypeArg> typeArgs;

    private ClassTypeSpec(QualifiedName name, List<TypeArg> typeArgs) {
        this.name = name;
        this.typeArgs = typeArgs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ClassTypeSpec of(String qualifiedName) {
        return of(QualifiedName.of(qualifiedName));
    }

    public static ClassTypeSpec of(QualifiedName name) {
        return new ClassTypeSpec(name, null);
    }

    @Override
    public void print(CodePrinter out) {
        out.print(name);
        if (typeArgs != null && !typeArgs.isEmpty()) {
            out.print("<", ">", ", ", o -> o.print(typeArgs));
        }
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

        public ClassTypeSpec build() {
            return new ClassTypeSpec(name, List.copyOf(typeArgs));
        }
    }
}

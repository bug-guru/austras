package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.TypeVarName;
import guru.bug.austras.code.spec.TypeSpec;

import java.util.List;

public class TypeParam implements Printable {
    private final TypeVarName typeVar;
    private final Bound bound;

    private TypeParam(TypeVarName typeVar, Bound bound) {
        this.typeVar = typeVar;
        this.bound = bound;
    }

    public static TypeParam of(TypeVarName typeVar) {
        return new TypeParam(typeVar, null);
    }

    public static TypeParam of(TypeVarName typeVar, TypeVarName boundVar) {
        return new TypeParam(typeVar, new TypeVarBound(boundVar));
    }

    public static TypeParam of(TypeVarName typeVar, TypeSpec bound) {
        return new TypeParam(typeVar, new TypeBound(bound));
    }

    public static TypeParam of(TypeVarName typeVar, TypeSpec... bounds) {
        return new TypeParam(typeVar, new TypeListBound(List.of(bounds)));
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(typeVar)
                .print(out.withWeakPrefix(o -> o.space().printExtends().space()),
                        o -> o.print(bound));
    }

    private static abstract class Bound implements Printable {
        // TODO
    }

    private static class TypeVarBound extends Bound {
        private final TypeVarName typeVar;

        private TypeVarBound(TypeVarName typeVar) {
            this.typeVar = typeVar;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(typeVar);
        }
    }

    private static class TypeBound extends Bound {
        private final TypeSpec type;

        private TypeBound(TypeSpec type) {
            this.type = type;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(type);
        }
    }

    private static class TypeListBound extends Bound {
        private final List<TypeSpec> types;

        private TypeListBound(List<TypeSpec> types) {
            this.types = types;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(out.withSeparator(" & "), o -> o.print(types));
        }
    }
}

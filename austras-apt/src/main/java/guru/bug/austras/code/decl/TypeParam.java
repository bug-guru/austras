package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.TypeVarName;
import guru.bug.austras.code.spec.ClassTypeSpec;

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

    public static TypeParam of(TypeVarName typeVar, ClassTypeSpec bound) {
        return new TypeParam(typeVar, new TypeBound(bound));
    }

    public static TypeParam of(TypeVarName typeVar, ClassTypeSpec... bounds) {
        return new TypeParam(typeVar, new TypeListBound(List.of(bounds)));
    }

    @Override
    public void print(CodePrinter out) {
        out.print(typeVar);
        if (bound != null) {
            out.space().printExtends().space().print(bound);
        }
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
        private final ClassTypeSpec type;

        private TypeBound(ClassTypeSpec type) {
            this.type = type;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(type);
        }
    }

    private static class TypeListBound extends Bound {
        private final List<ClassTypeSpec> types;

        private TypeListBound(List<ClassTypeSpec> types) {
            this.types = types;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(" & ", o -> o.print(types));
        }
    }
}

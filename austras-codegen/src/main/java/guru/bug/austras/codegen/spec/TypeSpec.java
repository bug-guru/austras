package guru.bug.austras.codegen.spec;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.QualifiedName;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class TypeSpec implements Printable {
    private static final TypeSpec VOID_TYPE = new TypeSpec(QualifiedName.of(null, "void"), null, true, false);
    private static final TypeSpec BOOLEAN_TYPE = new TypeSpec(QualifiedName.of(null, "boolean"), null, true, false);
    private static final TypeSpec BYTE_TYPE = new TypeSpec(QualifiedName.of(null, "byte"), null, true, false);
    private static final TypeSpec CHAR_TYPE = new TypeSpec(QualifiedName.of(null, "char"), null, true, false);
    private static final TypeSpec DOUBLE_TYPE = new TypeSpec(QualifiedName.of(null, "double"), null, true, false);
    private static final TypeSpec FLOAT_TYPE = new TypeSpec(QualifiedName.of(null, "float"), null, true, false);
    private static final TypeSpec INT_TYPE = new TypeSpec(QualifiedName.of(null, "int"), null, true, false);
    private static final TypeSpec LONG_TYPE = new TypeSpec(QualifiedName.of(null, "long"), null, true, false);
    private static final TypeSpec SHORT_TYPE = new TypeSpec(QualifiedName.of(null, "short"), null, true, false);
    private static final TypeSpec STRING_TYPE = new TypeSpec(QualifiedName.of("java.lang", "String"), null, true, false);
    private final QualifiedName name;
    private final List<TypeArg> typeArgs;
    private final boolean array;
    private final boolean primitive;

    private TypeSpec(QualifiedName name, List<TypeArg> typeArgs, boolean primitive, boolean array) {
        this.name = name;
        this.typeArgs = typeArgs;
        this.primitive = primitive;
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
        return new TypeSpec(name, typeArgs.length == 0 ? null : List.of(typeArgs), false, false);
    }

    public static TypeSpec ofPrimitive(String type) {
        return new TypeSpec(QualifiedName.of(null, type), null, true, false);
    }

    public static TypeSpec voidType() {
        return VOID_TYPE;
    }

    public static TypeSpec booleanType() {
        return BOOLEAN_TYPE;
    }

    public static TypeSpec byteType() {
        return BYTE_TYPE;
    }

    public static TypeSpec charType() {
        return CHAR_TYPE;
    }

    public static TypeSpec doubleType() {
        return DOUBLE_TYPE;
    }

    public static TypeSpec floatType() {
        return FLOAT_TYPE;
    }

    public static TypeSpec intType() {
        return INT_TYPE;
    }

    public static TypeSpec longType() {
        return LONG_TYPE;
    }

    public static TypeSpec shortType() {
        return SHORT_TYPE;
    }

    public static TypeSpec stringType() {
        return STRING_TYPE;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public QualifiedName getQualifiedName() {
        return name;
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

    @Override
    public String toString() {
        var sw = new StringWriter(100);
        var pw = new PrintWriter(sw);
        var cp = new CodePrinter(pw);
        print(cp);
        pw.close();
        return sw.toString();
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
            return new TypeSpec(name, List.copyOf(typeArgs), false, array);
        }
    }
}

package guru.bug.austras.code;

/**
 * Represents type parameter or type argument.
 */
public class TypeExt {
    private final TypeSpec bound;
    private final TypeExtBoundType boundType;
    private final String typeVar;

    private TypeExt(TypeSpec bound, TypeExtBoundType boundType, String typeVar) {
        this.bound = bound;
        this.boundType = boundType;
        this.typeVar = typeVar;
    }

    public static TypeExt diamond() {
        return new TypeExt(null, null, null);
    }

    public static TypeExt wildcard() {
        return new TypeExt(null, null, "?");
    }

    public static TypeExt wildcardExtends(TypeSpec upperBound) {
        return new TypeExt(upperBound, TypeExtBoundType.EXTENDS, "?");
    }

    public static TypeExt wildcardSuper(TypeSpec lowerBound) {
        return new TypeExt(lowerBound, TypeExtBoundType.SUPER, "?");
    }

    public static TypeExt param(String varName) {
        return new TypeExt(null, null, varName);
    }

    public static TypeExt paramExtends(String varName, TypeSpec upperBound) {
        return new TypeExt(upperBound, TypeExtBoundType.EXTENDS, varName);
    }

    public static TypeExt paramSuper(String varName, TypeSpec upperBound) {
        return new TypeExt(upperBound, TypeExtBoundType.SUPER, varName);
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        if (typeVar != null) {
            result.append(typeVar);
        }
        if (boundType != null) {
            space(result);
            result.append(boundType.keyword());
        }
        if (bound != null) {
            space(result);
            result.append(bound);
        }
        return result.toString();
    }

    private void space(StringBuilder result) {
        if (result.length() > 0) {
            result.append(' ');
        }
    }

    private enum TypeExtBoundType {
        SUPER("super"),
        EXTENDS("extends");

        private final String keyword;

        TypeExtBoundType(String keyword) {
            this.keyword = keyword;
        }

        public String keyword() {
            return keyword;
        }
    }
}

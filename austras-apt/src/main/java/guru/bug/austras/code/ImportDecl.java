package guru.bug.austras.code;

public class ImportDecl implements Writable {
    private final TypeName type;

    private ImportDecl(TypeName type) {
        this.type = type;
    }

    public static ImportDecl of(String qualifiedName) {
        var type = TypeName.of(qualifiedName);
        return new ImportDecl(type);
    }

    public static ImportDecl of(TypeName type) {
        return new ImportDecl(type);
    }

    public Result check(TypeName type) {
        if (this.type.equals(type)) {
            return Result.SAME;
        }
        if (this.type.getSimpleName().equals(type.getSimpleName())) {
            return Result.CONFLICT;
        }
        return Result.DIFFERENT;
    }

    @Override
    public void write(CodeWriter out) {
        out.write("import ");
        if (!type.getPackageSpec().isBlank()) {
            out.write(type.getPackageSpec());
            out.write(".");
        }
        out.write(type.getSimpleName());
        out.write(";" + System.lineSeparator());
    }

    public enum Result {
        DIFFERENT,
        SAME,
        CONFLICT
    }
}

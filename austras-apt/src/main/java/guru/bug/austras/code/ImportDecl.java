package guru.bug.austras.code;

public class ImportDecl implements Writable {
    private final QualifiedName type;

    private ImportDecl(QualifiedName type) {
        this.type = type;
    }

    public static ImportDecl of(String qualifiedName) {
        var type = QualifiedName.of(qualifiedName);
        return new ImportDecl(type);
    }

    public static ImportDecl of(QualifiedName type) {
        return new ImportDecl(type);
    }

    Result check(QualifiedName type) {
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
        if (!type.getPackageName().isRoot()) {
            out.write(type.getPackageName());
            out.write(".");
        }
        out.write(type.getSimpleName());
        out.write(";\n");
    }

    enum Result {
        DIFFERENT,
        SAME,
        CONFLICT
    }
}

package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodeWriter;
import guru.bug.austras.code.Writable;
import guru.bug.austras.code.name.QualifiedName;

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

    public boolean isSame(QualifiedName qname) {
        return this.type.equals(qname);
    }

    public boolean isConflict(QualifiedName qname) {
        return this.type.getSimpleName().equals(qname.getSimpleName());
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

}

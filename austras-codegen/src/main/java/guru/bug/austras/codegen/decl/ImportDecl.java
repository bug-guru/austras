package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.QualifiedName;

public class ImportDecl implements Printable {
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
        return this.type.getSimpleName().equals(qname.getSimpleName()) && !this.type.getPackageName().equals(qname.getPackageName());
    }

    @Override
    public void print(CodePrinter out) {
        out.printImport();
        type.printNoImport(out);
        out.print(";\n");
    }

}
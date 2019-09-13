package guru.bug.austras.codegen;

import guru.bug.austras.codegen.common.QualifiedName;
import guru.bug.austras.codegen.decl.ImportDecl;
import guru.bug.austras.codegen.decl.PackageDecl;
import guru.bug.austras.codegen.decl.TypeDecl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompilationUnit {
    private final PackageDecl packageDecl;
    private final List<TypeDecl> typeDecls;
    private final QualifiedName topLevel;

    private CompilationUnit(PackageDecl packageDecl, List<TypeDecl> typeDecls, QualifiedName topLevel) {
        this.packageDecl = packageDecl;
        this.typeDecls = typeDecls;
        this.topLevel = topLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void print(PrintWriter out) {
        var imports = new ArrayList<ImportDecl>();
        var buffer = new StringWriter(2048);
        var memOut = new PrintWriter(buffer);
        var currentPackage = packageDecl.getPackageName();

        var bufferOut = new CodePrinter(memOut, currentPackage, imports);
        bufferOut.print(typeDecls);

        var cw = new CodePrinter(out, currentPackage, imports);
        cw.print(packageDecl);
        if (!imports.isEmpty()) {
            cw.print("\n");
            cw.print(imports);
        }

        if (!typeDecls.isEmpty()) {
            cw.print("\n");
            out.print(buffer.toString());
        }
    }

    public String getQualifiedName() {
        return topLevel.asString();
    }


    public static class Builder {
        private PackageDecl packageSpec;
        private List<TypeDecl> typeDecls = new ArrayList<>();
        private QualifiedName topLevel;

        public Builder packageDecl(PackageDecl packageDecl) {
            this.packageSpec = packageDecl;
            return this;
        }

        public Builder addTypeDecl(TypeDecl typeDecl) {
            typeDecls.add(typeDecl);
            if (typeDecl.isTopLevel()) {
                topLevel = QualifiedName.of(packageSpec.getPackageName(), typeDecl.getSimpleName());
            }
            return this;
        }

        public Builder addTypeDecl(Collection<TypeDecl> list) {
            typeDecls.addAll(list);
            return this;
        }

        public CompilationUnit build() {
            return new CompilationUnit(packageSpec, List.copyOf(typeDecls), topLevel);
        }
    }

}

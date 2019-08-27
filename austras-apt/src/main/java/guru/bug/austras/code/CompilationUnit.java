package guru.bug.austras.code;

import guru.bug.austras.code.decl.ImportDecl;
import guru.bug.austras.code.decl.PackageDecl;
import guru.bug.austras.code.decl.TypeDecl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompilationUnit {

    private final PackageDecl packageDecl;
    private final List<TypeDecl> typeDecls;

    private CompilationUnit(PackageDecl packageDecl, List<TypeDecl> typeDecls) {
        this.packageDecl = packageDecl;
        this.typeDecls = typeDecls;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void write(PrintWriter out) {
        var imports = new ArrayList<ImportDecl>();
        var buffer = new PrintWriter(new StringWriter(2048));
        var currentPackage = packageDecl.getPackageName();

        var bufferOut = new CodeWriter(buffer, currentPackage, imports);
        for (var t : typeDecls) {
            t.write(bufferOut);
        }

        var cw = new CodeWriter(out, currentPackage, imports);
        cw.write(packageDecl);
        for (var i : imports) {
            cw.write(i);
        }
        cw.write(buffer.toString());
    }


    public static class Builder {
        private PackageDecl packageSpec;
        private List<TypeDecl> typeDecls = new ArrayList<>();

        public Builder packageDecl(PackageDecl packageDecl) {
            this.packageSpec = packageDecl;
            return this;
        }

        public Builder addTypeDecl(TypeDecl typeDecl) {
            typeDecls.add(typeDecl);
            return this;
        }

        public Builder addTypeDecl(Collection<TypeDecl> list) {
            typeDecls.addAll(list);
            return this;
        }

        public CompilationUnit build() {
            return new CompilationUnit(packageSpec, List.copyOf(typeDecls));
        }
    }

}

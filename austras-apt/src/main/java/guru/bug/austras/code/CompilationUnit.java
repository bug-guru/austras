package guru.bug.austras.code;

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

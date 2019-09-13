package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.PackageName;
import guru.bug.austras.codegen.spec.AnnotationSpec;

import java.util.ArrayList;
import java.util.List;

public class PackageDecl implements Printable {
    private final List<AnnotationSpec> annotations;
    private final PackageName packageName;

    private PackageDecl(List<AnnotationSpec> annotations, PackageName packageName) {
        this.annotations = annotations;
        this.packageName = packageName;
    }

    public static PackageDecl of(String packageName) {
        return new PackageDecl(null, PackageName.of(packageName));
    }

    public static Builder builder() {
        return new Builder();
    }

    public PackageName getPackageName() {
        return packageName;
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(out.withWeakSuffix("\n").separator("\n"),
                        o -> o.print(annotations))
                .printPackage()
                .print(packageName)
                .print(";\n");
    }

    public static class Builder {
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private PackageName packageName;

        public Builder packageSpec(String name) {
            this.packageName = PackageName.of(name);
            return this;
        }

        public Builder packageSpec(PackageName packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotations(List<AnnotationSpec> annotationSpecs) {
            this.annotations.addAll(annotationSpecs);
            return this;
        }

        public PackageDecl build() {
            return new PackageDecl(annotations, packageName);
        }
    }
}
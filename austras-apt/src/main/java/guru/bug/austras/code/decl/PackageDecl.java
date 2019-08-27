package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodeWriter;
import guru.bug.austras.code.Writable;
import guru.bug.austras.code.name.PackageName;
import guru.bug.austras.code.spec.AnnotationSpec;

import java.util.ArrayList;
import java.util.List;

public class PackageDecl implements Writable {
    private final List<AnnotationSpec> annotationSpecs;
    private final PackageName packageName;

    private PackageDecl(List<AnnotationSpec> annotationSpecs, PackageName packageName) {
        this.annotationSpecs = annotationSpecs;
        this.packageName = packageName;
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public static PackageDecl of(String packageName) {
        return new PackageDecl(null, PackageName.of(packageName));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void write(CodeWriter out) {

    }

    public static class Builder {
        private final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
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
            this.annotationSpecs.add(annotationSpec);
            return this;
        }

        public Builder addAnnotations(List<AnnotationSpec> annotationSpecs) {
            this.annotationSpecs.addAll(annotationSpecs);
            return this;
        }

        public PackageDecl build() {
            return new PackageDecl(annotationSpecs, packageName);
        }
    }
}

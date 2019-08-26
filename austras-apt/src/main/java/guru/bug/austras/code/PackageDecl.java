package guru.bug.austras.code;

import java.util.ArrayList;
import java.util.List;

public class PackageDecl implements Writable {
    private final List<AnnotationSpec> annotationSpecs;
    private final PackageSpec packageSpec;

    private PackageDecl(List<AnnotationSpec> annotationSpecs, PackageSpec packageSpec) {
        this.annotationSpecs = annotationSpecs;
        this.packageSpec = packageSpec;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
        private PackageSpec packageSpec;

        public Builder packageSpec(String name) {
            this.packageSpec = PackageSpec.of(name);
            return this;
        }

        public Builder packageSpec(PackageSpec packageSpec) {
            this.packageSpec = packageSpec;
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
            return new PackageDecl(annotationSpecs, packageSpec);
        }
    }
}

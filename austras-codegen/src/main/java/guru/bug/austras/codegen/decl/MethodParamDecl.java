package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.SimpleName;
import guru.bug.austras.codegen.spec.AnnotationSpec;
import guru.bug.austras.codegen.spec.TypeSpec;

import java.util.*;

public class MethodParamDecl implements Printable {
    private final List<Modifier> modifiers;
    private final List<AnnotationSpec> annotations;
    private final TypeSpec type;
    private final SimpleName name;

    private MethodParamDecl(List<Modifier> modifiers, List<AnnotationSpec> annotations, TypeSpec type, SimpleName name) {
        this.modifiers = modifiers;
        this.annotations = annotations;
        this.type = type;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(modifiers)
                .print(out.withSeparator(CodePrinter::space).weakSuffix(CodePrinter::space),
                        o -> o.print(annotations))
                .print(type)
                .space()
                .print(name);
    }

    public static class Builder {
        private Set<Modifier> modifiers;
        private List<AnnotationSpec> annotations;
        private TypeSpec type;
        private SimpleName name;

        private Set<Modifier> modifiers() {
            if (modifiers == null) {
                modifiers = new LinkedHashSet<>();
            }
            return modifiers;
        }

        private List<AnnotationSpec> annotations() {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            return annotations;
        }

        public Builder finalMod() {
            modifiers().add(Modifier.FINAL);
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotation) {
            annotations().add(annotation);
            return this;
        }

        public Builder addAnnotations(Collection<AnnotationSpec> annotations) {
            annotations().addAll(annotations);
            return this;
        }

        public Builder type(TypeSpec type) {
            this.type = type;
            return this;
        }

        public Builder type(String type) {
            this.type = TypeSpec.of(type);
            return this;
        }

        public Builder name(SimpleName name) {
            this.name = name;
            return this;
        }

        public Builder name(String name) {
            this.name = SimpleName.of(name);
            return this;
        }

        public MethodParamDecl build() {
            return new MethodParamDecl(
                    modifiers == null ? null : List.copyOf(modifiers),
                    annotations == null ? null : List.copyOf(annotations),
                    type,
                    name);
        }
    }
}

package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.name.SimpleName;
import guru.bug.austras.code.spec.AnnotationSpec;
import guru.bug.austras.code.spec.ClassTypeSpec;

import java.util.*;

class ClassTypeDecl extends TypeDecl {
    private final List<ClassModifier> modifiers;
    private final List<TypeParam> typeParams;
    private final List<AnnotationSpec> annotations;
    private final ClassTypeSpec superclass;
    private final List<ClassTypeSpec> superinterfaces;
    private final List<ClassMemberDecl> members;

    private ClassTypeDecl(SimpleName simpleName,
                          List<ClassModifier> modifiers, List<TypeParam> typeParams,
                          List<AnnotationSpec> annotations,
                          ClassTypeSpec superclass,
                          List<ClassTypeSpec> superinterfaces,
                          List<ClassMemberDecl> members) {
        super(simpleName);
        this.modifiers = modifiers;
        this.typeParams = typeParams;
        this.annotations = annotations;
        this.superclass = superclass;
        this.superinterfaces = superinterfaces;
        this.members = members;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(null, "\n", "\n", o -> o.print(annotations))
                .print(modifiers)
                .print(getSimpleName())
                .print("<", ">", ", ", o -> o.print(typeParams));
        if (superclass != null) {
            out.printExtends().print(superclass);
        }
        if (superinterfaces != null && !superinterfaces.isEmpty()) {
            out.printImplements().print(", ", o -> o.print(superinterfaces));
        }
        out.indent("{\n", "}\n", "\n", o -> o.print(members));
    }

    public static class Builder extends TypeDecl.Builder<Builder> {
        private Set<ClassModifier> modifiers;
        private List<TypeParam> typeParams;
        private List<AnnotationSpec> annotations;
        private ClassTypeSpec superclass;
        private List<ClassTypeSpec> superinterfaces;
        private List<ClassMemberDecl> members;

        private Set<ClassModifier> modifiers() {
            if (modifiers == null) {
                modifiers = new LinkedHashSet<>();
            }
            return modifiers;
        }

        public Builder modifiers(ClassModifier... modifiers) {
            modifiers().addAll(Arrays.asList(modifiers));
            return this;
        }

        private List<TypeParam> typeParams() {
            if (typeParams == null) {
                typeParams = new ArrayList<>();
            }
            return typeParams;
        }

        public Builder addTypeParameter(TypeParam typeParam) {
            typeParams().add(typeParam);
            return this;
        }

        public Builder addTypeParameters(Collection<TypeParam> typeParams) {
            typeParams().addAll(typeParams);
            return this;
        }

        private List<AnnotationSpec> annotations() {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            return annotations;
        }

        public Builder addAnnotation(AnnotationSpec annotation) {
            annotations().add(annotation);
            return this;
        }

        public Builder addAnnotations(Collection<AnnotationSpec> annotations) {
            annotations().addAll(annotations);
            return this;
        }

        public Builder superclass(ClassTypeSpec superclass) {
            this.superclass = superclass;
            return this;
        }

        private List<ClassTypeSpec> superinterfaces() {
            if (superinterfaces == null) {
                superinterfaces = new ArrayList<>();
            }
            return superinterfaces;
        }

        public Builder addSuperinterface(ClassTypeSpec superinterface) {
            superinterfaces().add(superinterface);
            return this;
        }

        public Builder addSuperinterfaces(Collection<ClassTypeSpec> superinterfaces) {
            superinterfaces().addAll(superinterfaces);
            return this;
        }

        private List<ClassMemberDecl> members() {
            if (members == null) {
                members = new ArrayList<>();
            }
            return members;
        }

        public Builder addMember(ClassMemberDecl memberDecl) {
            members().add(memberDecl);
            return this;
        }

        public Builder addMembers(Collection<ClassMemberDecl> members) {
            members().addAll(members);
            return this;
        }

        public TypeDecl build() {
            return new ClassTypeDecl(
                    simpleName,
                    modifiers == null ? null : List.copyOf(modifiers),
                    typeParams == null ? null : List.copyOf(typeParams),
                    annotations == null ? null : List.copyOf(annotations),
                    superclass,
                    superinterfaces == null ? null : List.copyOf(superinterfaces),
                    members == null ? null : List.copyOf(members)
            );
        }
    }
}

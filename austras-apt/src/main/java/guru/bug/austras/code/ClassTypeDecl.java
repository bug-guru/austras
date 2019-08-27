package guru.bug.austras.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassTypeDecl extends TypeDecl {
    private final List<TypeParam> typeParams;
    private final List<AnnotationSpec> annotationSpecs;
    private final ClassTypeSpec superclass;
    private final List<ClassTypeSpec> superinterfaces;
    private final List<ClassMemberDecl> members;

    private ClassTypeDecl(SimpleName simpleName,
                          List<TypeParam> typeParams,
                          List<AnnotationSpec> annotationSpecs,
                          ClassTypeSpec superclass,
                          List<ClassTypeSpec> superinterfaces,
                          List<ClassMemberDecl> members) {
        super(simpleName);
        this.typeParams = typeParams;
        this.annotationSpecs = annotationSpecs;
        this.superclass = superclass;
        this.superinterfaces = superinterfaces;
        this.members = members;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void write(CodeWriter out) {
    }

    public static class Builder extends TypeDecl.Builder<Builder> {
        private List<TypeParam> typeParams;
        private List<AnnotationSpec> annotations;
        private ClassTypeSpec superclass;
        private List<ClassTypeSpec> superinterfaces;
        private List<ClassMemberDecl> members;

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

        public Builder addSuperinterface(ClassTypeSpec superinterface) {
            superinterfaces().add(superinterface);
            return this;
        }

        public Builder addSuperinterfaces(Collection<ClassTypeSpec> superinterfaces) {
            superinterfaces().addAll(superinterfaces);
            return this;
        }

        private List<ClassTypeSpec> superinterfaces() {
            if (superinterfaces == null) {
                superinterfaces = new ArrayList<>();
            }
            return superinterfaces;
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

        public ClassTypeDecl build() {
            return new ClassTypeDecl(
                    simpleName,
                    typeParams == null ? null : List.copyOf(typeParams),
                    annotations == null ? null : List.copyOf(annotations),
                    superclass,
                    superinterfaces == null ? null : List.copyOf(superinterfaces),
                    members == null ? null : List.copyOf(members)
            );
        }
    }
}

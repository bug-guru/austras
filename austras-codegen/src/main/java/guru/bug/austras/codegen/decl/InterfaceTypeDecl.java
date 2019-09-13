package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.common.SimpleName;
import guru.bug.austras.codegen.spec.AnnotationSpec;
import guru.bug.austras.codegen.spec.TypeSpec;

import java.util.*;

public class InterfaceTypeDecl extends TypeDecl {
    private final List<Modifier> modifiers;
    private final List<TypeParam> typeParams;
    private final List<AnnotationSpec> annotations;
    private final List<TypeSpec> superinterfaces;
    private final List<ClassMemberDecl> members;

    private InterfaceTypeDecl(SimpleName simpleName,
                              List<Modifier> modifiers, List<TypeParam> typeParams,
                              List<AnnotationSpec> annotations,
                              List<TypeSpec> superinterfaces,
                              List<ClassMemberDecl> members) {
        super(simpleName);
        this.modifiers = modifiers;
        this.typeParams = typeParams;
        this.annotations = annotations;
        this.superinterfaces = superinterfaces;
        this.members = members;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(out.withWeakSuffix("\n").separator("\n"),
                        o -> o.print(annotations))
                .print(modifiers)
                .printInterface()
                .print(getSimpleName())
                .print(out.withWeakPrefix("<").weakSuffix(">").separator(", "),
                        o -> o.print(typeParams))
                .print(out.withWeakPrefix(o -> o.space().printExtends().space())
                                .separator(", "),
                        o -> o.print(superinterfaces))
                .print(out.withIndent(4).prefix(" {\n").suffix("}\n").separator("\n"),
                        o -> o.print(members));
    }

    @Override
    public boolean isTopLevel() {
        return modifiers != null && modifiers.contains(Modifier.PUBLIC);
    }

    public static class Builder extends TypeDecl.Builder<Builder> {
        private Set<Modifier> modifiers;
        private List<TypeParam> typeParams;
        private List<AnnotationSpec> annotations;
        private List<TypeSpec> superinterfaces;
        private List<ClassMemberDecl> members;

        private Set<Modifier> modifiers() {
            if (modifiers == null) {
                modifiers = new LinkedHashSet<>();
            }
            return modifiers;
        }

        public Builder publicMod() {
            modifiers().add(Modifier.PUBLIC);
            return this;
        }

        public Builder protectedMod() {
            modifiers().add(Modifier.PROTECTED);
            return this;
        }

        public Builder privateMod() {
            modifiers().add(Modifier.PRIVATE);
            return this;
        }

        public Builder abstractMod() {
            modifiers().add(Modifier.ABSTRACT);
            return this;
        }

        public Builder staticMod() {
            modifiers().add(Modifier.STATIC);
            return this;
        }

        public Builder finalMod() {
            modifiers().add(Modifier.FINAL);
            return this;
        }

        public Builder strictfpMod() {
            modifiers().add(Modifier.STRICTFP);
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

        private List<TypeSpec> superinterfaces() {
            if (superinterfaces == null) {
                superinterfaces = new ArrayList<>();
            }
            return superinterfaces;
        }

        public Builder addSuperinterface(TypeSpec superinterface) {
            superinterfaces().add(superinterface);
            return this;
        }

        public Builder addSuperinterfaces(Collection<TypeSpec> superinterfaces) {
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
            return new InterfaceTypeDecl(
                    simpleName,
                    modifiers == null ? null : List.copyOf(modifiers),
                    typeParams == null ? null : List.copyOf(typeParams),
                    annotations == null ? null : List.copyOf(annotations),
                    superinterfaces == null ? null : List.copyOf(superinterfaces),
                    members == null ? null : List.copyOf(members)
            );
        }
    }
}

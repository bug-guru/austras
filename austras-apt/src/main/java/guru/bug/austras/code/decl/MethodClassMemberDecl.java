package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.CodeBlock;
import guru.bug.austras.code.common.SimpleName;
import guru.bug.austras.code.spec.AnnotationSpec;
import guru.bug.austras.code.spec.ClassTypeSpec;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MethodClassMemberDecl extends ClassMemberDecl implements Printable {
    private final List<Modifier> modifiers;
    private final ClassTypeSpec returnType;
    private final SimpleName name;
    private final List<MethodParamDecl> params;
    private final List<AnnotationSpec> annotations;
    private final CodeBlock body;

    private MethodClassMemberDecl(List<Modifier> modifiers, ClassTypeSpec returnType, SimpleName name, List<MethodParamDecl> params, List<AnnotationSpec> annotations, CodeBlock body) {
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.annotations = annotations;
        this.body = body;
    }

    public static Builder constructorBuilder() {
        return new Builder(null, null);
    }

    public static Builder methodBuilder(String name, ClassTypeSpec returnType) {
        return new Builder(SimpleName.of(name), returnType);
    }

    public static Builder methodBuilder(SimpleName name, ClassTypeSpec returnType) {
        return new Builder(name, returnType);
    }

    @Override
    public void print(CodePrinter out) {

    }

    public static class Builder {
        private final SimpleName name;
        private final ClassTypeSpec returnType;
        private Set<Modifier> modifiers;
        private List<AnnotationSpec> annotations;
        private List<MethodParamDecl> params;
        private CodeBlock body;

        public Builder(SimpleName name, ClassTypeSpec returnType) {
            this.name = name;
            this.returnType = returnType;
        }

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

        public Builder synchronizedMod() {
            modifiers().add(Modifier.SYNCHRONIZED);
            return this;
        }

        public Builder strictfpMod() {
            modifiers().add(Modifier.STRICTFP);
            return this;
        }

        public Builder nativeMod() {
            modifiers().add(Modifier.NATIVE);
            return this;
        }

        public MethodClassMemberDecl build() {
            return new MethodClassMemberDecl(
                    List.copyOf(modifiers),
                    returnType,
                    name,
                    List.copyOf(params),
                    List.copyOf(annotations),
                    body);
        }
    }
}

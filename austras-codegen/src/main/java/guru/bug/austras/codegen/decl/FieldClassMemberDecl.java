package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.CodePrinter;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.CodeBlock;
import guru.bug.austras.codegen.common.SimpleName;
import guru.bug.austras.codegen.spec.TypeSpec;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FieldClassMemberDecl extends ClassMemberDecl implements Printable {
    private final List<Modifier> modifiers;
    private final TypeSpec type;
    private final SimpleName name;
    private final CodeBlock initializer;

    private FieldClassMemberDecl(List<Modifier> modifiers, TypeSpec type, SimpleName name, CodeBlock initializer) {
        this.modifiers = modifiers;
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    public static Builder builder(TypeSpec type, String name) {
        return new Builder(type, name);
    }

    @Override
    public void print(CodePrinter out) {
        out
                .print(out.withSeparator(CodePrinter::space), o -> o.print(modifiers))
                .print(type)
                .space()
                .print(name)
                .print(out.withWeakPrefix(" = "), o -> o.print(initializer))
                .print(";");
    }

    public static class Builder {
        private Set<Modifier> modifiers;
        private TypeSpec type;
        private SimpleName name;
        private CodeBlock initializer;

        public Builder(TypeSpec type, String name) {
            this.type = type;
            this.name = SimpleName.of(name);
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

        public Builder initializer(CodeBlock initializer) {
            this.initializer = initializer;
            return this;
        }

        public FieldClassMemberDecl build() {
            return new FieldClassMemberDecl(
                    modifiers == null ? null : List.copyOf(modifiers),
                    type,
                    name,
                    initializer);
        }
    }
}

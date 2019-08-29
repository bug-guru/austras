package guru.bug.austras.code.decl;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.CodeBlock;
import guru.bug.austras.code.common.SimpleName;
import guru.bug.austras.code.spec.AnnotationSpec;
import guru.bug.austras.code.spec.ClassTypeSpec;

import java.util.ArrayList;
import java.util.List;

public class MethodClassMemberDecl extends ClassMemberDecl implements Printable {
    private final ClassTypeSpec returnType;
    private final SimpleName name;
    private final List<MethodParamDecl> params;
    private final List<AnnotationSpec> annotations;
    private final CodeBlock body;

    private MethodClassMemberDecl(ClassTypeSpec returnType, SimpleName name, List<MethodParamDecl> params, List<AnnotationSpec> annotations, CodeBlock body) {
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
        private List<AnnotationSpec> annotations;
        private List<MethodParamDecl> params;
        private CodeBlock body;

        public Builder(SimpleName name, ClassTypeSpec returnType) {
            this.name = name;
            this.returnType = returnType;
        }

        public MethodClassMemberDecl build() {
            return new MethodClassMemberDecl(returnType, name, params, annotations, body);
        }
    }
}

package guru.bug.austras.code.decl;

import guru.bug.austras.code.Printable;
import guru.bug.austras.code.spec.TypeSpec;

public abstract class ClassMemberDecl implements Printable {

    public static MethodClassMemberDecl.Builder constructorBuilder() {
        return MethodClassMemberDecl.constructorBuilder();
    }

    public static MethodClassMemberDecl.Builder methodBuilder(String name, TypeSpec returnType) {
        return MethodClassMemberDecl.methodBuilder(name, returnType);
    }
}

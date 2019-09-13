package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.spec.TypeSpec;

public abstract class ClassMemberDecl implements Printable {

    public static MethodClassMemberDecl.Builder constructorBuilder() {
        return MethodClassMemberDecl.constructorBuilder();
    }

    public static MethodClassMemberDecl.Builder methodBuilder(String name, TypeSpec returnType) {
        return MethodClassMemberDecl.methodBuilder(name, returnType);
    }

    public static FieldClassMemberDecl.Builder fieldBulder(TypeSpec type, String name) {
        return FieldClassMemberDecl.builder(type, name);
    }
}

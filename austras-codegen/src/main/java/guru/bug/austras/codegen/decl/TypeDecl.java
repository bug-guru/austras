package guru.bug.austras.codegen.decl;

import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.SimpleName;

public abstract class TypeDecl implements Printable {
    private final SimpleName simpleName;

    TypeDecl(SimpleName simpleName) {
        this.simpleName = simpleName;
    }

    public SimpleName getSimpleName() {
        return simpleName;
    }

    public static ClassTypeDecl.Builder classBuilder() {
        return ClassTypeDecl.builder();
    }

    public static InterfaceTypeDecl.Builder interfaceBuilder() {
        return InterfaceTypeDecl.builder();
    }

    public abstract boolean isTopLevel();

    protected static abstract class Builder<T extends Builder> {
        SimpleName simpleName;

        public T simpleName(SimpleName simpleName) {
            this.simpleName = simpleName;
            //noinspection unchecked
            return (T) this;
        }

        public T simpleName(String simpleName) {
            this.simpleName = SimpleName.of(simpleName);
            //noinspection unchecked
            return (T) this;
        }
    }


}

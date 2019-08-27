package guru.bug.austras.code.decl;

import guru.bug.austras.code.Writable;
import guru.bug.austras.code.name.SimpleName;

public abstract class TypeDecl implements Writable {
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

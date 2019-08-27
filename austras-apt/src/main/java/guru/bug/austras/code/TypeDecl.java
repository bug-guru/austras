package guru.bug.austras.code;

public abstract class TypeDecl implements Writable {
    private final SimpleName simpleName;


    protected TypeDecl(SimpleName simpleName) {
        this.simpleName = simpleName;
    }

    public SimpleName getSimpleName() {
        return simpleName;
    }

    public static abstract class Builder<T extends Builder> {
        protected SimpleName simpleName;

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

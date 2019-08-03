package guru.bug.austras.provider;

public abstract class LocalComponentProvider<T> extends AbstractComponentProvider<T> {

    @Override
    public T get() {
        return takeInstance();
    }
}

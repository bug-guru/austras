package guru.bug.fuzzbagel.provider;

public abstract class AbstractComponentProvider<T> implements ComponentProvider<T> {
    protected abstract T takeInstance();

    @Override
    public abstract T get();
}

package guru.bug.fuzzbagel.provider;

public abstract class LocalComponentProvider<T> extends AbstractComponentProvider<T> {

    @Override
    public T get() {
        return takeInstance();
    }
}

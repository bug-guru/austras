package guru.bug.fuzzbagel.privider;

public abstract class LocalComponentProvider<T> extends AbstractComponentProvider<T> {

    @Override
    public T get() {
        return takeInstance();
    }
}

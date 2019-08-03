package guru.bug.austras.provider;

public abstract class GlobalComponentProvider<T> extends AbstractComponentProvider<T> {
    private T instance;

    @Override
    public T get() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = takeInstance();
                }
            }
        }
        return instance;
    }
}

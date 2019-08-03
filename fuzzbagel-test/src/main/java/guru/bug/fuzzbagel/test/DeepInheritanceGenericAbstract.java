package guru.bug.fuzzbagel.test;

public abstract class DeepInheritanceGenericAbstract<T> implements DeepInheritanceGeneric<T> {
    @Override
    public void doSomething(T t) {

    }

    protected abstract T anotherSomething();
}

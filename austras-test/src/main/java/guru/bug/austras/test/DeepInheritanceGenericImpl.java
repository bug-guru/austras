package guru.bug.austras.test;

public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

package guru.bug.austras.test;

import guru.bug.austras.annotations.NoCached;

@NoCached
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

package guru.bug.austras.test;

import guru.bug.austras.core.Component;

@SuppressWarnings("ALL")
@Component
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

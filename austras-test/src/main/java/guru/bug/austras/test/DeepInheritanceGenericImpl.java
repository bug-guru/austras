package guru.bug.austras.test;

import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings("ALL")
@Default
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

package guru.bug.austras.test;

import guru.bug.austras.core.Component;
import guru.bug.austras.scopes.Cache;
import guru.bug.austras.scopes.LazyThreadLocalScopeCache;

@Cache(LazyThreadLocalScopeCache.class)
@Component
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

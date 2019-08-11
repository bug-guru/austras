package guru.bug.austras.test;

import guru.bug.austras.annotations.Cached;
import guru.bug.austras.annotations.Component;
import guru.bug.austras.provider.LazyThreadLocalScopeCache;

@Cached(LazyThreadLocalScopeCache.class)
@Component
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}

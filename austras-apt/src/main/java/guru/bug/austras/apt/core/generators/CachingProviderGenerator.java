package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.provider.ScopeCache;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.List;

public class CachingProviderGenerator implements ProviderGenerator {
    public CachingProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencyModels, Class<? extends ScopeCache> value) {

    }

    @Override
    public void generateProvider() {
        // TODO
    }
}

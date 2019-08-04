package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;

import javax.annotation.processing.ProcessingEnvironment;

public class NonCachingProviderGenerator implements ProviderGenerator {
    public NonCachingProviderGenerator(ProcessingEnvironment processingEnv) {

    }

    @Override
    public void generateProvider(ComponentModel cm) {
        // TODO
    }
}

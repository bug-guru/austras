package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;

public interface ProviderGenerator {
    void generateProvider(ComponentModel cm);
}

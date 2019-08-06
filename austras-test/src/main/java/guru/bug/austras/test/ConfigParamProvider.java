package guru.bug.austras.test;

import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.provider.Provider;

@Qualifier("@guru.bug.austras.annotations.Config(name=\"config\")")
public class ConfigParamProvider implements Provider<String> {

    @Override
    public String get() {
        return "bla bla bla";
    }
}

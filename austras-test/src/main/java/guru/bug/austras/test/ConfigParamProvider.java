package guru.bug.austras.test;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;
import guru.bug.austras.provider.Provider;

@Qualifier(name = "ConfigurationProperty", properties = @QualifierProperty(name = "name", value = "config\"test\""))
public class ConfigParamProvider implements Provider<String> {
    public ConfigParamProvider(ComponentA a, ComponentB b, ComponentC c) {
    }

    @Override
    public String get() {
        return "bla bla bla";
    }
}

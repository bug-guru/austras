package guru.bug.austras.test;

import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.annotations.QualifierProperty;
import guru.bug.austras.provider.Provider;

@Qualifier(name = "ConfigurationProperty", properties = @QualifierProperty(name = "name", value = "config"))
public class ConfigParamProvider implements Provider<String> {

    @Override
    public String get() {
        return "bla bla bla";
    }
}

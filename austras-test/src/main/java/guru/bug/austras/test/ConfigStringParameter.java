package guru.bug.austras.test;

import guru.bug.austras.config.Config;

public class ConfigStringParameter {

    public ConfigStringParameter(ComponentE e, @Config(name = "config\"test\"", defaultValue = "test") String config) {
    }
}

package guru.bug.austras.test;

import guru.bug.austras.annotations.Config;

public class ConfigStringParameter {

    public ConfigStringParameter(ComponentE e, @Config(name = "config", defaultValue = "test") String config) {
    }
}

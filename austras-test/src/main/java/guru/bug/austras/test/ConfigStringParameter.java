package guru.bug.austras.test;

import guru.bug.austras.annotations.Config;
import guru.bug.austras.annotations.NoCached;

@NoCached
public class ConfigStringParameter {

    public ConfigStringParameter(ComponentE e, @Config(name = "config\"test\"", defaultValue = "test") String config) {
    }
}

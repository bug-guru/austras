package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.Duration;

public class DurationToStringConverter implements StringConverter<Duration> {
    @Override
    public Duration fromString(String value) {
        if (value == null) {
            return null;
        }
        return Duration.parse(value);
    }

    @Override
    public String toString(Duration obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}

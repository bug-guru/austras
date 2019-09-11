package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.LocalTime;

public class LocalTimeToStringConverter implements StringConverter<LocalTime> {
    @Override
    public LocalTime fromString(String value) {
        if (value == null) return null;
        return LocalTime.parse(value);
    }

    @Override
    public String toString(LocalTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.Instant;

public class InstantToStringConverter implements StringConverter<Instant> {
    @Override
    public Instant fromString(String value) {
        if (value == null) return null;
        return Instant.parse(value);
    }

    @Override
    public String toString(Instant obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

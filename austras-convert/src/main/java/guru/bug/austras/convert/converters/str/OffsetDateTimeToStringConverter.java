package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.OffsetDateTime;

public class OffsetDateTimeToStringConverter implements StringConverter<OffsetDateTime> {
    @Override
    public OffsetDateTime fromString(String value) {
        if (value == null) return null;
        return OffsetDateTime.parse(value);
    }

    @Override
    public String toString(OffsetDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

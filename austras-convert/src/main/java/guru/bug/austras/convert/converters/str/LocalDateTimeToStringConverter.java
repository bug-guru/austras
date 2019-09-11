package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.LocalDateTime;

public class LocalDateTimeToStringConverter implements StringConverter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String value) {
        if (value == null) return null;
        return LocalDateTime.parse(value);
    }

    @Override
    public String toString(LocalDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

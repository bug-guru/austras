package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDateTime;

@Default
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

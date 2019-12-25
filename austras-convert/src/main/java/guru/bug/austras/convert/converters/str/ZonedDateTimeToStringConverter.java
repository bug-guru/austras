package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZonedDateTime;

@Default
public class ZonedDateTimeToStringConverter implements StringConverter<ZonedDateTime> {
    @Override
    public ZonedDateTime fromString(String value) {
        if (value == null) return null;
        return ZonedDateTime.parse(value);
    }

    @Override
    public String toString(ZonedDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

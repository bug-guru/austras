package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.time.OffsetTime;

@Component
public class OffsetTimeToStringConverter implements StringConverter<OffsetTime> {
    @Override
    public OffsetTime fromString(String value) {
        if (value == null) return null;
        return OffsetTime.parse(value);
    }

    @Override
    public String toString(OffsetTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

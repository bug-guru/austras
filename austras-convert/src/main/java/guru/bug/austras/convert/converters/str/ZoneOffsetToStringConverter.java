package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.time.ZoneOffset;

@Component
public class ZoneOffsetToStringConverter implements StringConverter<ZoneOffset> {
    @Override
    public ZoneOffset fromString(String value) {
        if (value == null) return null;
        return ZoneOffset.of(value);
    }

    @Override
    public String toString(ZoneOffset obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZoneId;

@Default
public class ZoneIdToStringConverter implements StringConverter<ZoneId> {
    @Override
    public ZoneId fromString(String value) {
        if (value == null) return null;
        return ZoneId.of(value);
    }

    @Override
    public String toString(ZoneId obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringShortConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveShortToStringConverter implements StringShortConverter {
    @Override
    public short fromString(String value) {
        return Short.parseShort(value);
    }

    @Override
    public String toString(short value) {
        return Short.toString(value);
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringShortConverter;

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

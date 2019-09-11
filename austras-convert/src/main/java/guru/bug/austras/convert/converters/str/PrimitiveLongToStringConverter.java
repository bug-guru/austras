package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringLongConverter;

public class PrimitiveLongToStringConverter implements StringLongConverter {
    @Override
    public long fromString(String value) {
        return Long.parseLong(value);
    }

    @Override
    public String toString(long value) {
        return Long.toString(value);
    }
}

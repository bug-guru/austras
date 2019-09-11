package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringByteConverter;

public class PrimitiveByteToStringConverter implements StringByteConverter {
    @Override
    public byte fromString(String value) {
        return Byte.parseByte(value);
    }

    @Override
    public String toString(byte obj) {
        return String.valueOf(obj);
    }
}

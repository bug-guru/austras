package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringByteConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
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

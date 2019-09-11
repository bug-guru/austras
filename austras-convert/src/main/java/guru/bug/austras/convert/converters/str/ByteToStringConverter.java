package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringByteConverter;
import guru.bug.austras.convert.converters.StringConverter;

public class ByteToStringConverter implements StringConverter<Byte> {
    private final StringByteConverter stringConverter;

    public ByteToStringConverter(StringByteConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Byte fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Byte value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}

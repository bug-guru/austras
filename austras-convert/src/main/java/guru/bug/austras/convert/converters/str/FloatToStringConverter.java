package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.converters.StringFloatConverter;

public class FloatToStringConverter implements StringConverter<Float> {
    private final StringFloatConverter stringConverter;

    public FloatToStringConverter(StringFloatConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Float fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Float value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringFloatConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveFloatToStringConverter implements StringFloatConverter {
    @Override
    public float fromString(String value) {
        return Float.parseFloat(value);
    }

    @Override
    public String toString(float value) {
        return Float.toString(value);
    }
}

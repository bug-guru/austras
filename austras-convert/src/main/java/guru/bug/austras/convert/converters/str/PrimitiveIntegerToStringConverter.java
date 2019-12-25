package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringIntegerConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveIntegerToStringConverter implements StringIntegerConverter {
    @Override
    public int fromString(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toString(int value) {
        return Integer.toString(value);
    }
}

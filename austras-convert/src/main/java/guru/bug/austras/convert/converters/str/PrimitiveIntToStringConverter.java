package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringIntConverter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveIntToStringConverter implements StringIntConverter {
    @Override
    public int fromString(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toString(int value) {
        return Integer.toString(value);
    }
}

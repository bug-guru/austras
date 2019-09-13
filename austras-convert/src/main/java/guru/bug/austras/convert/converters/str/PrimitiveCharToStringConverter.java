package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringCharConverter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveCharToStringConverter implements StringCharConverter {
    @Override
    public char fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String toString(char value) {
        return Character.toString(value);
    }
}

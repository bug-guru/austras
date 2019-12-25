package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringCharacterConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveCharacterToStringConverter implements StringCharacterConverter {
    @Override
    public char fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String toString(char value) {
        return Character.toString(value);
    }
}

package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringCharConverter;
import guru.bug.austras.convert.converters.StringConverter;

public class CharacterToStringConverter implements StringConverter<Character> {
    private final StringCharConverter stringConverter;

    public CharacterToStringConverter(StringCharConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Character fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Character value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
